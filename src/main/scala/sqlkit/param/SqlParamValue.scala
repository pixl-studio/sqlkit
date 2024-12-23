package sqlkit.param

import sqlkit.SqlColumn

import java.sql.PreparedStatement
import java.util.UUID
import sqlkit.binder.SqlBinder

import java.time.{Duration, Instant, LocalDate, LocalDateTime, LocalTime}


sealed trait SqlParamValue {
  def set(ps: PreparedStatement, index: Int): Unit
}
trait Wrapper[T] { val value: T }

object SqlParamValue {

  def apply[T](v:T)(implicit binder:SqlBinder[T]) = {
    new SqlParamValue with Wrapper[T] {
      val value = v
      def set(ps: PreparedStatement, index: Int) = {
        binder.toSql(v, ps, index)
      }
    }
  }

  /*
    Common wrap types. Use SqlParam(...) if missing
   */
  def wrap(p:Any): SqlParamValue = {
    p match {

      case v:Boolean => SqlParamValue(v)

      case v:Short => SqlParamValue(v)
      case v:Int => SqlParamValue(v)
      case v:Long => SqlParamValue(v)
      case v:Double => SqlParamValue(v)
      case v:Float => SqlParamValue(v)
      case v:BigDecimal => SqlParamValue(v)

      case v:Duration => SqlParamValue(v)
      case v:Instant => SqlParamValue(v)
      case v:LocalDate => SqlParamValue(v)
      case v:LocalDateTime => SqlParamValue(v)
      case v:LocalTime => SqlParamValue(v)

      case v:UUID => SqlParamValue(v)

      case v:SqlParamValue => v

      case v:Any => SqlParamValue(v.toString)
    }
  }

  implicit def toParameterValue[T](v: T)(implicit binder:SqlBinder[T]): SqlParamValue = SqlParamValue(v)

}