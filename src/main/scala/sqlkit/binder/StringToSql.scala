package sqlkit.binder
import java.sql.PreparedStatement

class StringToSql extends SqlBinder[String] {

  override def fromSql(o: Object): String = {
    o.toString
  }

  override def toSql(v: String, statement: PreparedStatement, index: Int): Unit = {
    statement.setString(index, v)
  }

  def unwrap(o:String) = o
}
