package sqlkit

import java.sql.ResultSet
import sqlkit.binder.SqlBinder
import sqlkit.param.{SqlParam, SqlParamValue, Wrapper}

case class SqlColumn[T](
  name: String, primaryKey: Boolean = false
)(implicit table: SqlTableDef[_], binder: SqlBinder[T]) {

  def toSqlString: String = {
    if (table.alias.isEmpty) s"""${name}""" else s"""${table.alias}.${name}"""
  }

  def toSqlParam: String = {
    if (table.alias.isEmpty) s"""${name}""" else s"""${table.alias}_${name}"""
  }

  def get(rs: ResultSet) = {
    binder(rs, toSqlString)
  }

  def -> (v:T): (SqlColumn[T], SqlParamValue with Wrapper[T]) = {
    (this, SqlParamValue[T](v)(binder))
  }

  def getBinder = binder
}