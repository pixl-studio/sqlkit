package sqlkit

import com.zaxxer.hikari.HikariDataSource
import sqlkit.exceptions.UnknownDataSourceException
import sqlkit.logger.{InMemoryLogger, SqlLogger}
import sqlkit.query.SqlQueryCommon
import sqlkit.session.{IsolationLevel, SqlAuto, SqlSession, SqlTx}
import sqlkit.utils.Timing

import javax.sql.DataSource


object DB {

  val defaultSource = "default"

  private var dataSources = Map.empty[String, DataSource]
  private var dataSourcesLogging = Set.empty[String]

  private var queryLogger: SqlLogger = null //new InMemoryLogger()

  def dataSource(name: String = defaultSource): DataSource = {
    dataSources.getOrElse(name, throw UnknownDataSourceException(name))
  }

  def add(dataSource: DataSource, name: String = defaultSource, log: Boolean = false) = {
    dataSources = dataSources + (name -> dataSource)
    if (log) {
      dataSourcesLogging += name
    }
  }

  def close(name: String = defaultSource) = {
    dataSource(name) match {
      case d: HikariDataSource => d.close()
      case _ =>
    }
  }

  def log(query: SqlQueryCommon[_], time: Option[Long], rows: Option[Long])(implicit session: SqlSession) = {

    if (shouldLog(session.dataSource)) {

      import scala.jdk.CollectionConverters.IteratorHasAsScala

      val queryInfos = List(
        time.map { t => Timing.timeFormat("duration", duration = t) },
        rows.map { r => s"rows: ${r}" },
        Some(s"id: ${session.uuid}")
      ).flatten

      val stackTrace = StackWalker
        .getInstance(java.lang.StackWalker.Option.RETAIN_CLASS_REFERENCE)
        .walk(frames => List.from(frames.skip(7).iterator().asScala))
        .map(el => s"${el.getClassName}.${el.getMethodName}(${el.getFileName}:${el.getLineNumber})")
        .take(10)

      session.logger.debug(
        s"""
           |### SQL QUERY:  ${queryInfos.mkString(",  ")}
           |${query.debugSql}
           |Stack:  ${stackTrace.mkString("\n\t")}
           |""".stripMargin)
    }

    if (queryLogger != null) {
      queryLogger.log(query, time, rows)
    }
  }

  def enableLog(dataSource: String) = {
    dataSourcesLogging += dataSource
  }

  def disableLog(dataSource: String) = {
    dataSourcesLogging -= dataSource
  }

  def shouldLog(dataSource: String): Boolean = {
    dataSourcesLogging.contains(dataSource)
  }

  def logger = {
    queryLogger
  }

  // ---

  def withSession[T](dataSource: String = defaultSource)(f: SqlSession => T): T = {
    f(SqlAuto(dataSource))
  }

  def withTransaction[T](dataSource: String = DB.defaultSource,
                          schema: Option[String] = None,
                          isolationLevel: IsolationLevel = IsolationLevel.Default
                        )(f: SqlTx => T): T = SqlTx.localTx(dataSource, schema, isolationLevel)(f)
}