package sqlkit

import com.zaxxer.hikari.HikariDataSource
import sqlkit.exceptions.UnknownDataSourceException
import sqlkit.logger.{InMemoryLogger, SqlLogger}
import sqlkit.query.SqlQueryCommon
import sqlkit.session.{IsolationLevel, SqlAuto, SqlSession, SqlTx}


object DB {

  val defaultSource = "default"

  private var dataSources = Map.empty[String, HikariDataSource]

  private var queryLogger:SqlLogger = new InMemoryLogger()

  def dataSource(name: String=defaultSource): HikariDataSource = {
    dataSources.getOrElse(name, throw UnknownDataSourceException(name))
  }

  def add(dataSource: HikariDataSource, name: String=defaultSource) = {
    dataSources = dataSources + (name -> dataSource)
  }

  def close(name:String=defaultSource) = {
    dataSource(name).close()
  }

  def log(query:SqlQueryCommon[_], time:Option[Long], rows:Option[Long])(implicit session: SqlSession) = {
    if (shouldLog){
      queryLogger.log(query, time, rows)
    }
  }

  def shouldLog : Boolean = {
    queryLogger != null
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