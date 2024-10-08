package sqlkit.binder.time

import sqlkit.binder.SqlBinder

import java.sql.{PreparedStatement, Timestamp}
import java.time.{OffsetDateTime, ZoneId}

class OffsetDateTimeToSql extends SqlBinder[OffsetDateTime] {

  override def fromSql(o: Object): OffsetDateTime = {
    o match {
      case s: Timestamp => OffsetDateTime.ofInstant(s.toInstant, ZoneId.systemDefault())
      case _ => unwrap(o.toString)
    }
  }

  override def toSql(v: OffsetDateTime, statement: PreparedStatement, index: Int): Unit = {
    statement.setTimestamp(index, Timestamp.from(v.toInstant))
  }

  def unwrap(o:String) = OffsetDateTime.parse(o)
}