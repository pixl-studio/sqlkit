package sqlkit.binder.time

import sqlkit.binder.SqlBinder

import java.sql.{PreparedStatement, Timestamp}
import java.time.Instant

class InstantToSql extends SqlBinder[Instant] {

  override def fromSql(o: Object): Instant = {
    o match {
      case s: Timestamp => s.toInstant
      case _ => unwrap(o.toString)
    }
  }

  override def toSql(v: Instant, statement: PreparedStatement, index: Int): Unit = {
    statement.setTimestamp(index, Timestamp.from(v))
  }

  def unwrap(o:String) = Instant.parse(o)
}