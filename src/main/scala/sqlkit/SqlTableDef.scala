package sqlkit

import sqlkit.binder.SqlBinder
import sqlkit.binder.crypto.{CryptedToSql, Crypto}
import sqlkit.param.SqlParamValue

import scala.util.Try

abstract class SqlTableDef[T] {

  val table: String
  val id: SqlColumn[_]
  val alias: String

  def fromSql(row: SqlRow): T

  def toSql(entity: T): List[(SqlColumn[_], SqlParamValue)]

  def fromSqlOpt(row: SqlRow): Option[T] = {
    Try { fromSql(row) }.toOption
  }

  private[this] var columns = List.empty[SqlColumn[_]]

  protected def column[T](name: String, primaryKey: Boolean = false)(implicit binder: SqlBinder[T]): SqlColumn[T] = {
    val column = SqlColumn[T](name, primaryKey)(this, binder)
    columns :+= column
    column
  }

  def columnCrypted[T](name: String, primaryKey: Boolean = false)(crypto: Crypto)(implicit binder: SqlBinder[T]): SqlColumn[T] = {
    column[T](name, primaryKey)(CryptedToSql[T](crypto))
  }

  def * = columns

  def toSqlString = if (alias.isEmpty) s"""${table}""" else s"""${table} as ${alias}"""

}