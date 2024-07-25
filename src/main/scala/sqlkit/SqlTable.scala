package sqlkit

import sqlkit.binder.SqlBinder
import sqlkit.param.{SqlParam, SqlParams}
import sqlkit.session._

abstract class SqlTable[T <: SqlModel[T]] {

  implicit def self: SqlTable[T] = this

  type TableDef <: SqlTableDef[T]

  def table(alias: String): TableDef

  def autoSession = SqlAuto()

  private def selectSql(model: TableDef): SqlQuery[T] = {
    sql"""
      SELECT ${model.*}
      FROM ${model}
    """.as(model)
  }

  def list(implicit session: SqlSession=autoSession): List[T] = {
    withSql(table) { model =>
      selectSql(model)
    }.list
  }

  def listP(offset:Option[Long]=None, limit:Option[Long]=None)(implicit session: SqlSession = autoSession): SqlList[T] = {
    withSql(table) { model =>
      selectSql(model)
    }.list(offset, limit)
  }

  def where(whereSql: TableDef => SqlQuery[_])(implicit session: SqlSession = autoSession): SqlQuery[T] = {
    withSql(table) { model =>
      val q = whereSql(model).as(model)
      val finalSql = if (q.isEmpty) selectSql(model) else selectSql(model) ++ " WHERE " ++ q
      finalSql
    }
  }

  private val byIdSql = where { model =>
    sql"""${model.id} = :id"""
  }
  def byId[U](id: U)(implicit session: SqlSession = autoSession, pkBinder: SqlBinder[U]): Option[T] = {
    byIdSql
      .on("id" -> id)
      .single
  }


  def insert(entity: T)(implicit session: SqlSession = autoSession): T = {
    val result = insertSql(entity).exec
    entity
  }

  def batchInsert(entities: List[T], batchSize:Int=100)(implicit session: SqlSession = autoSession): List[T] = {
    entities.headOption.map { entity =>
      insertSql(entity).execBatch(
        entities.map(insertSqlParam), batchSize
      )
      entities
    }.getOrElse(Nil)
  }

  protected def insertSql[K](entity: T)(implicit session: SqlSession = autoSession): SqlQuery[Unit] = {
    val model = table("")
    val columns = model.toSql(entity)

    sql"""
      INSERT INTO ${model} (${columns.map { case (column, _) => column }})
      VALUES (${SqlParams(columns.map { case (col, value) => SqlParam(col.name, value) })})
    """
  }
  protected def insertSqlParam[K](entity: T)(implicit session: SqlSession = autoSession): List[SqlParam] = {
    val model = table("")
    val columns = model.toSql(entity)
    columns.map { case (col, value) => SqlParam(col.name, value) }
  }

  def update(entity: T)(implicit session: SqlSession = autoSession): T = {
    execUpdate(entity)
    entity
  }

  def execUpdate(entity: T)(implicit session: SqlSession = autoSession): SqlResult[Unit] = {
    withSql(table) { model =>
      val (pkColumns, columns) = model.toSql(entity).partition(_._1.primaryKey)
      sql"""
        UPDATE ${model}
        SET ${SqlColumns(columns, " ,")}
        WHERE ${SqlColumns(pkColumns, " AND ")}
      """
    }.exec
  }

  def delete(entity: T)(implicit session: SqlSession = autoSession): Long = {

    val model = table("")
    val (pkColumns, _) = model.toSql(entity).partition(_._1.primaryKey)

    val result = sql"""
      DELETE FROM ${model}
      WHERE ${SqlColumns(pkColumns, " AND ")}
    """.exec

    result.affectedRows
  }

}