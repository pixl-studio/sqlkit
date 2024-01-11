package sqlkit

import sqlkit.param.SqlParamValue

case class SqlColumns(
  columns: List[(SqlColumn[_], SqlParamValue)],
  mkString:String
)
