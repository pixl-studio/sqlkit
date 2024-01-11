package sqlkit

import sqlkit.binder.SqlBinder

import java.sql.ResultSet

case class SqlRow(rs: ResultSet) {
  def get[T](col: SqlColumn[T]): T = {
    col.get(rs)
  }

  def get[T](columnIndex: Int)(implicit binder: SqlBinder[T]): T = {
    binder(rs, columnIndex)
  }

  def get[T](columnName: String)(implicit binder: SqlBinder[T]): T = {
    binder(rs, columnName)
  }
}