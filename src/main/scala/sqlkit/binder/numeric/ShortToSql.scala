package sqlkit.binder.numeric

import sqlkit.binder.SqlBinder

import java.sql.PreparedStatement

class ShortToSql extends SqlBinder[Short] {

  override def fromSql(o: Object): Short = {
    o match {
      case s:java.lang.Short => s
      case _ => unwrap(o.toString)
    }
  }

  override def toSql(v: Short, statement: PreparedStatement, index: Int): Unit = {
    statement.setShort(index, v)
  }

  override def unwrap(o: String): Short = java.lang.Short.valueOf(o)
}
