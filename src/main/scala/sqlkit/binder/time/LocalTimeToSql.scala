package sqlkit.binder.time

import sqlkit.binder.SqlBinder

import java.sql.PreparedStatement
import java.time.LocalTime

class LocalTimeToSql extends SqlBinder[LocalTime] {

  override def fromSql(o: Object): LocalTime = {
    unwrap(o.toString)
  }

  override def toSql(v: LocalTime, statement: PreparedStatement, index: Int): Unit = {
    statement.setString(index, v.toString)
  }

  def unwrap(o:String) = LocalTime.parse(o)
}

