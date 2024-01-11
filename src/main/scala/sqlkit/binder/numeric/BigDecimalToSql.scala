package sqlkit.binder.numeric

import sqlkit.binder.SqlBinder

import java.sql.PreparedStatement

class BigDecimalToSql extends SqlBinder[BigDecimal] {

  override def fromSql(o: Object): BigDecimal = {
    o match {
      case s:java.math.BigDecimal => s
      case _ => unwrap(o.toString)
    }
  }

  override def toSql(v: BigDecimal, statement: PreparedStatement, index: Int): Unit = {
    statement.setBigDecimal(index, v.bigDecimal)
  }

  override def unwrap(o: String): BigDecimal = BigDecimal(o)
}
