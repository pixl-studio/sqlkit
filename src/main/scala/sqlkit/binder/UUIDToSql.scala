package sqlkit.binder

import java.sql.PreparedStatement
import java.util.UUID

class UUIDToSql extends SqlBinder[UUID] {

  override def fromSql(o: Object): UUID = {
    o match {
      case s: UUID => s
      case _ => unwrap(o.toString)
    }
  }

  override def toSql(v: UUID, statement: PreparedStatement, index: Int): Unit = {
    statement.setString(index, v.toString)
  }

  def unwrap(o:String) = UUID.fromString(o)
}
