package sqlkit.binder.time

import sqlkit.binder.SqlBinder

import java.sql.PreparedStatement
import java.time.Duration

class DurationToSql extends SqlBinder[Duration] {

  override def fromSql(o: Object): Duration = {
    unwrap(o.toString)
  }

  override def toSql(v: Duration, statement: PreparedStatement, index: Int): Unit = {
    statement.setString(index, v.toString)
  }

  override def unwrap(o: String): Duration = Duration.parse(o)
}

