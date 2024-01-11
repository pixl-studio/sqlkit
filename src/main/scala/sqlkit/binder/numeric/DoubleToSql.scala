package sqlkit.binder.numeric

import sqlkit.binder.SqlBinder

import java.sql.PreparedStatement

class DoubleToSql extends SqlBinder[Double] {

  override def fromSql(o: Object): Double = {
    o match {
      case s:java.lang.Double => s
      case _ => unwrap(o.toString)
    }
  }

  override def toSql(v: Double, statement: PreparedStatement, index: Int): Unit = {
    statement.setDouble(index, v)
  }

  override def unwrap(o: String): Double = java.lang.Double.valueOf(o)
}
