package sqlkit.param

import java.sql.{PreparedStatement, ResultSet}

import sqlkit.binder.SqlBinder

/** Applied named parameter. */
sealed case class SqlParam(name: String, value: SqlParamValue) {
  def set(ps:PreparedStatement, index:Int) = {
    value.set(ps, index)
  }
}

/** Companion object for applied named parameter. */
object SqlParam {

  import scala.language.implicitConversions

  def apply[T](v:T)(implicit binder:SqlBinder[T]): SqlParamValue = SqlParamValue(v)

  /**
    * Conversion to use tuple, with first element being name
    * of parameter as string.
    *
    * {{{
    * val p: Parameter = ("name" -> 1l)
    * }}}
    */
  implicit def string[V](t: (String, V))(implicit c: V => SqlParamValue): SqlParam = SqlParam(t._1, c(t._2))

  /**
    * Conversion to use tuple,
    * with first element being symbolic name or parameter.
    *
    * {{{
    * val p: Parameter = ('name -> 1l)
    * }}}
    */
  implicit def symbol[V](t: (Symbol, V))(implicit c: V => SqlParamValue): SqlParam = SqlParam(t._1.name, c(t._2))

}
