package sqlkit.binder.numeric

import sqlkit.binder.SqlBinder

import java.sql.PreparedStatement

class IntToSql extends SqlBinder[Int] {

  override def fromSql(o: Object): Int = {
    o match {
      case s:java.lang.Integer => s
      case _ => unwrap(o.toString)
    }
  }

  override def toSql(v: Int, statement: PreparedStatement, index: Int): Unit = {
    statement.setInt(index, v)
  }

  override def unwrap(o: String): Int = java.lang.Integer.valueOf(o)
}
