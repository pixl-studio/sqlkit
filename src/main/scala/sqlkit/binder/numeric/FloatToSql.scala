package sqlkit.binder.numeric

import sqlkit.binder.SqlBinder

import java.sql.PreparedStatement

class FloatToSql extends SqlBinder[Float] {

  override def fromSql(o: Object): Float = {
    o match {
      case s:java.lang.Float => s
      case _ => unwrap(o.toString)
    }
  }

  override def toSql(v: Float, statement: PreparedStatement, index: Int): Unit = {
    statement.setFloat(index, v)
  }

  override def unwrap(o: String): Float = java.lang.Float.valueOf(o)
}
