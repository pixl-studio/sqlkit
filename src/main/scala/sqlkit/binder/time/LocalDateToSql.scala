package sqlkit.binder.time

import sqlkit.binder.SqlBinder

import java.sql.{Date, PreparedStatement}
import java.time.LocalDate

class LocalDateToSql extends SqlBinder[LocalDate] {

  override def fromSql(o: Object): LocalDate = {
    o match {
      case s: Date => s.toLocalDate
      case _ => unwrap(o.toString)
    }
  }

  override def toSql(v: LocalDate, statement: PreparedStatement, index: Int): Unit = {
    statement.setDate(index, Date.valueOf(v))
  }

  def unwrap(o:String) = LocalDate.parse(o)
}

