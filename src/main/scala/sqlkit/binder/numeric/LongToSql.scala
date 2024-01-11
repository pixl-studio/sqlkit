package sqlkit.binder.numeric

import sqlkit.binder.SqlBinder

import java.sql.PreparedStatement

class LongToSql extends SqlBinder[Long] {

  override def fromSql(o: Object): Long = {
    o match {
      case s:java.lang.Long => s
      case _ => unwrap(o.toString)
    }
  }

  override def toSql(v: Long, statement: PreparedStatement, index: Int): Unit = {
    statement.setLong(index, v)
  }

  override def unwrap(o: String): Long = java.lang.Long.valueOf(o)
}
