package sqlkit

import java.sql.ResultSet

class SqlResultSet[T](
  rs:ResultSet,
  fromSql:SqlRow => T
) extends Iterator[T] {

  private var rows = 0L

  override def hasNext: Boolean = {
    rs.next()
  }

  override def next(): T = {
    rows += 1
    fromSql(SqlRow(rs))
  }

  def getRows(): Long = {
    rows
  }
}