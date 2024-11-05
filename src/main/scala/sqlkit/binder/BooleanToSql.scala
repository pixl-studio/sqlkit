package sqlkit.binder

import java.sql.PreparedStatement

class BooleanToSql extends SqlBinder[Boolean] {

  override def fromSql(o: Object): Boolean = {
    o match {
      case b:java.lang.Boolean => b
      case _ => unwrap(o.toString)
    }
  }

  override def toSql(v: Boolean, statement: PreparedStatement, index: Int): Unit = {
    statement.setBoolean(index, v)
  }

  def unwrap(o:String) = {
    o match {
      case s:String if s == "1" => true
      case s:String if s == "0" => false
      case s:String =>  s.toBoolean
    }
  }
}
