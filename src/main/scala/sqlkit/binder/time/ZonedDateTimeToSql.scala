package sqlkit.binder.time

import sqlkit.binder.SqlBinder

import java.sql.{PreparedStatement, Timestamp}
import java.time.{ZoneId, ZonedDateTime}

class ZonedDateTimeToSql extends SqlBinder[ZonedDateTime] {

  override def fromSql(o: Object): ZonedDateTime = {
    o match {
      case s: Timestamp => ZonedDateTime.ofInstant(s.toInstant, ZoneId.systemDefault())
      case _ => unwrap(o.toString)
    }
  }

  override def toSql(v: ZonedDateTime, statement: PreparedStatement, index: Int): Unit = {
    statement.setTimestamp(index, Timestamp.from(v.toInstant))
  }

  def unwrap(o:String) = ZonedDateTime.parse(o)
}