package sqlkit.binder.time

import sqlkit.binder.SqlBinder

import java.sql.{Date, PreparedStatement, Timestamp}
import java.time.LocalDateTime

class LocalDateTimeToSql extends SqlBinder[LocalDateTime] {

  override def fromSql(o: Object): LocalDateTime = {
    o match {
      case s: Date => LocalDateTime.from(s.toInstant)
      case _ => unwrap(o.toString)
    }
  }

  override def toSql(v: LocalDateTime, statement: PreparedStatement, index: Int): Unit = {
    statement.setTimestamp(index, Timestamp.valueOf(v))
  }

  def unwrap(o:String) = LocalDateTime.parse(o)
}

