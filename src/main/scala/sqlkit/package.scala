import scala.jdk.CollectionConverters.ConcurrentMapHasAsScala
import scala.jdk.OptionConverters.RichOptional

package object sqlkit {

  implicit def sqlInterpolation(sc: StringContext): SqlInterpolation = new SqlInterpolation(sc)

  def withSql[U, T1 <: SqlTableDef[_]](t1: String => T1)(f: (T1) => U): U = f(
    t1("x1")
  )

  def withSqlCompiled[U, T1 <: SqlTableDef[_]](t1: String => T1)(f: (T1) => U): U = memoize(
    withSql(t1)(f)
  )

  def withSql[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_]](t1: String => T1, t2: String => T2)(f: (T1, T2) => U): U = f(
    t1("x1"), t2("x2")
  )

  def withSqlCompiled[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_]](t1: String => T1, t2: String => T2)(f: (T1, T2) => U): U = memoize(
    withSql(t1, t2)(f)
  )

  def withSql[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_], T3 <: SqlTableDef[_]](t1: String => T1, t2: String => T2, t3: String => T3)(f: (T1, T2, T3) => U): U = f(
    t1("x1"), t2("x2"), t3("x3")
  )

  def withSqlCompiled[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_], T3 <: SqlTableDef[_]](t1: String => T1, t2: String => T2, t3: String => T3)(f: (T1, T2, T3) => U): U = memoize(
    withSql(t1, t2, t3)(f)
  )

  def withSql[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_], T3 <: SqlTableDef[_], T4 <: SqlTableDef[_]](t1: String => T1, t2: String => T2, t3: String => T3, t4: String => T4)(f: (T1, T2, T3, T4) => U): U = f(
    t1("x1"), t2("x2"), t3("x3"), t4("x4")
  )

  def withSqlCompiled[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_], T3 <: SqlTableDef[_], T4 <: SqlTableDef[_]](t1: String => T1, t2: String => T2, t3: String => T3, t4: String => T4)(f: (T1, T2, T3, T4) => U): U = memoize(
    withSql(t1, t2, t3, t4)(f)
  )

  def withSql[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_], T3 <: SqlTableDef[_], T4 <: SqlTableDef[_], T5 <: SqlTableDef[_]](t1: String => T1, t2: String => T2, t3: String => T3, t4: String => T4, t5: String => T5)(f: (T1, T2, T3, T4, T5) => U): U = f(
    t1("x1"), t2("x2"), t3("x3"), t4("x4"), t5("x5")
  )

  def withSqlCompiled[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_], T3 <: SqlTableDef[_], T4 <: SqlTableDef[_], T5 <: SqlTableDef[_]](t1: String => T1, t2: String => T2, t3: String => T3, t4: String => T4, t5: String => T5)(f: (T1, T2, T3, T4, T5) => U): U = memoize(
    withSql(t1, t2, t3, t4, t5)(f)
  )

  def withSql[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_], T3 <: SqlTableDef[_], T4 <: SqlTableDef[_], T5 <: SqlTableDef[_], T6 <: SqlTableDef[_]](t1: String => T1, t2: String => T2, t3: String => T3, t4: String => T4, t5: String => T5, t6: String => T6)(f: (T1, T2, T3, T4, T5, T6) => U): U = f(
    t1("x1"), t2("x2"), t3("x3"), t4("x4"), t5("x5"), t6("x6")
  )

  def withSqlCompiled[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_], T3 <: SqlTableDef[_], T4 <: SqlTableDef[_], T5 <: SqlTableDef[_], T6 <: SqlTableDef[_]](t1: String => T1, t2: String => T2, t3: String => T3, t4: String => T4, t5: String => T5, t6: String => T6)(f: (T1, T2, T3, T4, T5, T6) => U): U = memoize(
    withSql(t1, t2, t3, t4, t5, t6)(f)
  )

  def withSql[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_], T3 <: SqlTableDef[_], T4 <: SqlTableDef[_], T5 <: SqlTableDef[_], T6 <: SqlTableDef[_], T7 <: SqlTableDef[_]](t1: String => T1, t2: String => T2, t3: String => T3, t4: String => T4, t5: String => T5, t6: String => T6, t7: String => T7)(f: (T1, T2, T3, T4, T5, T6, T7) => U): U = f(
    t1("x1"), t2("x2"), t3("x3"), t4("x4"), t5("x5"), t6("x6"), t7("x7")
  )

  def withSqlCompiled[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_], T3 <: SqlTableDef[_], T4 <: SqlTableDef[_], T5 <: SqlTableDef[_], T6 <: SqlTableDef[_], T7 <: SqlTableDef[_]](t1: String => T1, t2: String => T2, t3: String => T3, t4: String => T4, t5: String => T5, t6: String => T6, t7: String => T7)(f: (T1, T2, T3, T4, T5, T6, T7) => U): U = memoize(
    withSql(t1, t2, t3, t4, t5, t6, t7)(f)
  )

  def withSql[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_], T3 <: SqlTableDef[_], T4 <: SqlTableDef[_], T5 <: SqlTableDef[_], T6 <: SqlTableDef[_], T7 <: SqlTableDef[_], T8 <: SqlTableDef[_]](t1: String => T1, t2: String => T2, t3: String => T3, t4: String => T4, t5: String => T5, t6: String => T6, t7: String => T7, t8: String => T8)(f: (T1, T2, T3, T4, T5, T6, T7, T8) => U): U = f(
    t1("x1"), t2("x2"), t3("x3"), t4("x4"), t5("x5"), t6("x6"), t7("x7"), t8("x8")
  )

  def withSqlCompiled[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_], T3 <: SqlTableDef[_], T4 <: SqlTableDef[_], T5 <: SqlTableDef[_], T6 <: SqlTableDef[_], T7 <: SqlTableDef[_], T8 <: SqlTableDef[_]](t1: String => T1, t2: String => T2, t3: String => T3, t4: String => T4, t5: String => T5, t6: String => T6, t7: String => T7, t8: String => T8)(f: (T1, T2, T3, T4, T5, T6, T7, T8) => U): U = memoize(
    withSql(t1, t2, t3, t4, t5, t6, t7, t8)(f)
  )

  def withSql[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_], T3 <: SqlTableDef[_], T4 <: SqlTableDef[_], T5 <: SqlTableDef[_], T6 <: SqlTableDef[_], T7 <: SqlTableDef[_], T8 <: SqlTableDef[_], T9 <: SqlTableDef[_]](t1: String => T1, t2: String => T2, t3: String => T3, t4: String => T4, t5: String => T5, t6: String => T6, t7: String => T7, t8: String => T8, t9: String => T9)(f: (T1, T2, T3, T4, T5, T6, T7, T8, T9) => U): U = f(
    t1("x1"), t2("x2"), t3("x3"), t4("x4"), t5("x5"), t6("x6"), t7("x7"), t8("x8"), t9("x9")
  )

  def withSqlCompiled[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_], T3 <: SqlTableDef[_], T4 <: SqlTableDef[_], T5 <: SqlTableDef[_], T6 <: SqlTableDef[_], T7 <: SqlTableDef[_], T8 <: SqlTableDef[_], T9 <: SqlTableDef[_]](t1: String => T1, t2: String => T2, t3: String => T3, t4: String => T4, t5: String => T5, t6: String => T6, t7: String => T7, t8: String => T8, t9: String => T9)(f: (T1, T2, T3, T4, T5, T6, T7, T8, T9) => U): U = memoize(
    withSql(t1, t2, t3, t4, t5, t6, t7, t8, t9)(f)
  )

  def withSql[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_], T3 <: SqlTableDef[_], T4 <: SqlTableDef[_], T5 <: SqlTableDef[_], T6 <: SqlTableDef[_], T7 <: SqlTableDef[_], T8 <: SqlTableDef[_], T9 <: SqlTableDef[_], T10 <: SqlTableDef[_]](t1: String => T1, t2: String => T2, t3: String => T3, t4: String => T4, t5: String => T5, t6: String => T6, t7: String => T7, t8: String => T8, t9: String => T9, t10: String => T10)(f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) => U): U = f(
    t1("x1"), t2("x2"), t3("x3"), t4("x4"), t5("x5"), t6("x6"), t7("x7"), t8("x8"), t9("x9"), t10("x10")
  )

  def withSqlCompiled[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_], T3 <: SqlTableDef[_], T4 <: SqlTableDef[_], T5 <: SqlTableDef[_], T6 <: SqlTableDef[_], T7 <: SqlTableDef[_], T8 <: SqlTableDef[_], T9 <: SqlTableDef[_], T10 <: SqlTableDef[_]](t1: String => T1, t2: String => T2, t3: String => T3, t4: String => T4, t5: String => T5, t6: String => T6, t7: String => T7, t8: String => T8, t9: String => T9, t10: String => T10)(f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) => U): U = memoize(
    withSql(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10)(f)
  )

  def withSql[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_], T3 <: SqlTableDef[_], T4 <: SqlTableDef[_], T5 <: SqlTableDef[_], T6 <: SqlTableDef[_], T7 <: SqlTableDef[_], T8 <: SqlTableDef[_], T9 <: SqlTableDef[_], T10 <: SqlTableDef[_], T11 <: SqlTableDef[_]](t1: String => T1, t2: String => T2, t3: String => T3, t4: String => T4, t5: String => T5, t6: String => T6, t7: String => T7, t8: String => T8, t9: String => T9, t10: String => T10, t11: String => T11)(f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) => U): U = f(
    t1("x1"), t2("x2"), t3("x3"), t4("x4"), t5("x5"), t6("x6"), t7("x7"), t8("x8"), t9("x9"), t10("x10"), t11("x11")
  )

  def withSqlCompiled[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_], T3 <: SqlTableDef[_], T4 <: SqlTableDef[_], T5 <: SqlTableDef[_], T6 <: SqlTableDef[_], T7 <: SqlTableDef[_], T8 <: SqlTableDef[_], T9 <: SqlTableDef[_], T10 <: SqlTableDef[_], T11 <: SqlTableDef[_]](t1: String => T1, t2: String => T2, t3: String => T3, t4: String => T4, t5: String => T5, t6: String => T6, t7: String => T7, t8: String => T8, t9: String => T9, t10: String => T10, t11: String => T11)(f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) => U): U = memoize(
    withSql(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11)(f)
  )

  def withSql[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_], T3 <: SqlTableDef[_], T4 <: SqlTableDef[_], T5 <: SqlTableDef[_], T6 <: SqlTableDef[_], T7 <: SqlTableDef[_], T8 <: SqlTableDef[_], T9 <: SqlTableDef[_], T10 <: SqlTableDef[_], T11 <: SqlTableDef[_], T12 <: SqlTableDef[_]](t1: String => T1, t2: String => T2, t3: String => T3, t4: String => T4, t5: String => T5, t6: String => T6, t7: String => T7, t8: String => T8, t9: String => T9, t10: String => T10, t11: String => T11, t12: String => T12)(f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) => U): U = f(
    t1("x1"), t2("x2"), t3("x3"), t4("x4"), t5("x5"), t6("x6"), t7("x7"), t8("x8"), t9("x9"), t10("x10"), t11("x11"), t12("x12")
  )

  def withSqlCompiled[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_], T3 <: SqlTableDef[_], T4 <: SqlTableDef[_], T5 <: SqlTableDef[_], T6 <: SqlTableDef[_], T7 <: SqlTableDef[_], T8 <: SqlTableDef[_], T9 <: SqlTableDef[_], T10 <: SqlTableDef[_], T11 <: SqlTableDef[_], T12 <: SqlTableDef[_]](t1: String => T1, t2: String => T2, t3: String => T3, t4: String => T4, t5: String => T5, t6: String => T6, t7: String => T7, t8: String => T8, t9: String => T9, t10: String => T10, t11: String => T11, t12: String => T12)(f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) => U): U = memoize(
    withSql(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12)(f)
  )

  def withSql[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_], T3 <: SqlTableDef[_], T4 <: SqlTableDef[_], T5 <: SqlTableDef[_], T6 <: SqlTableDef[_], T7 <: SqlTableDef[_], T8 <: SqlTableDef[_], T9 <: SqlTableDef[_], T10 <: SqlTableDef[_], T11 <: SqlTableDef[_], T12 <: SqlTableDef[_], T13 <: SqlTableDef[_]](t1: String => T1, t2: String => T2, t3: String => T3, t4: String => T4, t5: String => T5, t6: String => T6, t7: String => T7, t8: String => T8, t9: String => T9, t10: String => T10, t11: String => T11, t12: String => T12, t13: String => T13)(f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13) => U): U = f(
    t1("x1"), t2("x2"), t3("x3"), t4("x4"), t5("x5"), t6("x6"), t7("x7"), t8("x8"), t9("x9"), t10("x10"), t11("x11"), t12("x12"), t13("x13")
  )

  def withSqlCompiled[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_], T3 <: SqlTableDef[_], T4 <: SqlTableDef[_], T5 <: SqlTableDef[_], T6 <: SqlTableDef[_], T7 <: SqlTableDef[_], T8 <: SqlTableDef[_], T9 <: SqlTableDef[_], T10 <: SqlTableDef[_], T11 <: SqlTableDef[_], T12 <: SqlTableDef[_], T13 <: SqlTableDef[_]](t1: String => T1, t2: String => T2, t3: String => T3, t4: String => T4, t5: String => T5, t6: String => T6, t7: String => T7, t8: String => T8, t9: String => T9, t10: String => T10, t11: String => T11, t12: String => T12, t13: String => T13)(f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13) => U): U = memoize(
    withSql(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13)(f)
  )

  def withSql[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_], T3 <: SqlTableDef[_], T4 <: SqlTableDef[_], T5 <: SqlTableDef[_], T6 <: SqlTableDef[_], T7 <: SqlTableDef[_], T8 <: SqlTableDef[_], T9 <: SqlTableDef[_], T10 <: SqlTableDef[_], T11 <: SqlTableDef[_], T12 <: SqlTableDef[_], T13 <: SqlTableDef[_], T14 <: SqlTableDef[_]](t1: String => T1, t2: String => T2, t3: String => T3, t4: String => T4, t5: String => T5, t6: String => T6, t7: String => T7, t8: String => T8, t9: String => T9, t10: String => T10, t11: String => T11, t12: String => T12, t13: String => T13, t14: String => T14)(f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14) => U): U = f(
    t1("x1"), t2("x2"), t3("x3"), t4("x4"), t5("x5"), t6("x6"), t7("x7"), t8("x8"), t9("x9"), t10("x10"), t11("x11"), t12("x12"), t13("x13"), t14("x14")
  )

  def withSqlCompiled[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_], T3 <: SqlTableDef[_], T4 <: SqlTableDef[_], T5 <: SqlTableDef[_], T6 <: SqlTableDef[_], T7 <: SqlTableDef[_], T8 <: SqlTableDef[_], T9 <: SqlTableDef[_], T10 <: SqlTableDef[_], T11 <: SqlTableDef[_], T12 <: SqlTableDef[_], T13 <: SqlTableDef[_], T14 <: SqlTableDef[_]](t1: String => T1, t2: String => T2, t3: String => T3, t4: String => T4, t5: String => T5, t6: String => T6, t7: String => T7, t8: String => T8, t9: String => T9, t10: String => T10, t11: String => T11, t12: String => T12, t13: String => T13, t14: String => T14)(f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14) => U): U = memoize(
    withSql(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14)(f)
  )

  def withSql[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_], T3 <: SqlTableDef[_], T4 <: SqlTableDef[_], T5 <: SqlTableDef[_], T6 <: SqlTableDef[_], T7 <: SqlTableDef[_], T8 <: SqlTableDef[_], T9 <: SqlTableDef[_], T10 <: SqlTableDef[_], T11 <: SqlTableDef[_], T12 <: SqlTableDef[_], T13 <: SqlTableDef[_], T14 <: SqlTableDef[_], T15 <: SqlTableDef[_]](t1: String => T1, t2: String => T2, t3: String => T3, t4: String => T4, t5: String => T5, t6: String => T6, t7: String => T7, t8: String => T8, t9: String => T9, t10: String => T10, t11: String => T11, t12: String => T12, t13: String => T13, t14: String => T14, t15: String => T15)(f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15) => U): U = f(
    t1("x1"), t2("x2"), t3("x3"), t4("x4"), t5("x5"), t6("x6"), t7("x7"), t8("x8"), t9("x9"), t10("x10"), t11("x11"), t12("x12"), t13("x13"), t14("x14"), t15("x15")
  )

  def withSqlCompiled[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_], T3 <: SqlTableDef[_], T4 <: SqlTableDef[_], T5 <: SqlTableDef[_], T6 <: SqlTableDef[_], T7 <: SqlTableDef[_], T8 <: SqlTableDef[_], T9 <: SqlTableDef[_], T10 <: SqlTableDef[_], T11 <: SqlTableDef[_], T12 <: SqlTableDef[_], T13 <: SqlTableDef[_], T14 <: SqlTableDef[_], T15 <: SqlTableDef[_]](t1: String => T1, t2: String => T2, t3: String => T3, t4: String => T4, t5: String => T5, t6: String => T6, t7: String => T7, t8: String => T8, t9: String => T9, t10: String => T10, t11: String => T11, t12: String => T12, t13: String => T13, t14: String => T14, t15: String => T15)(f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15) => U): U = memoize(
    withSql(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15)(f)
  )

  def withSql[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_], T3 <: SqlTableDef[_], T4 <: SqlTableDef[_], T5 <: SqlTableDef[_], T6 <: SqlTableDef[_], T7 <: SqlTableDef[_], T8 <: SqlTableDef[_], T9 <: SqlTableDef[_], T10 <: SqlTableDef[_], T11 <: SqlTableDef[_], T12 <: SqlTableDef[_], T13 <: SqlTableDef[_], T14 <: SqlTableDef[_], T15 <: SqlTableDef[_], T16 <: SqlTableDef[_]](t1: String => T1, t2: String => T2, t3: String => T3, t4: String => T4, t5: String => T5, t6: String => T6, t7: String => T7, t8: String => T8, t9: String => T9, t10: String => T10, t11: String => T11, t12: String => T12, t13: String => T13, t14: String => T14, t15: String => T15, t16: String => T16)(f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16) => U): U = f(
    t1("x1"), t2("x2"), t3("x3"), t4("x4"), t5("x5"), t6("x6"), t7("x7"), t8("x8"), t9("x9"), t10("x10"), t11("x11"), t12("x12"), t13("x13"), t14("x14"), t15("x15"), t16("x16")
  )

  def withSqlCompiled[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_], T3 <: SqlTableDef[_], T4 <: SqlTableDef[_], T5 <: SqlTableDef[_], T6 <: SqlTableDef[_], T7 <: SqlTableDef[_], T8 <: SqlTableDef[_], T9 <: SqlTableDef[_], T10 <: SqlTableDef[_], T11 <: SqlTableDef[_], T12 <: SqlTableDef[_], T13 <: SqlTableDef[_], T14 <: SqlTableDef[_], T15 <: SqlTableDef[_], T16 <: SqlTableDef[_]](t1: String => T1, t2: String => T2, t3: String => T3, t4: String => T4, t5: String => T5, t6: String => T6, t7: String => T7, t8: String => T8, t9: String => T9, t10: String => T10, t11: String => T11, t12: String => T12, t13: String => T13, t14: String => T14, t15: String => T15, t16: String => T16)(f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16) => U): U = memoize(
    withSql(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16)(f)
  )

  def withSql[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_], T3 <: SqlTableDef[_], T4 <: SqlTableDef[_], T5 <: SqlTableDef[_], T6 <: SqlTableDef[_], T7 <: SqlTableDef[_], T8 <: SqlTableDef[_], T9 <: SqlTableDef[_], T10 <: SqlTableDef[_], T11 <: SqlTableDef[_], T12 <: SqlTableDef[_], T13 <: SqlTableDef[_], T14 <: SqlTableDef[_], T15 <: SqlTableDef[_], T16 <: SqlTableDef[_], T17 <: SqlTableDef[_]](t1: String => T1, t2: String => T2, t3: String => T3, t4: String => T4, t5: String => T5, t6: String => T6, t7: String => T7, t8: String => T8, t9: String => T9, t10: String => T10, t11: String => T11, t12: String => T12, t13: String => T13, t14: String => T14, t15: String => T15, t16: String => T16, t17: String => T17)(f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17) => U): U = f(
    t1("x1"), t2("x2"), t3("x3"), t4("x4"), t5("x5"), t6("x6"), t7("x7"), t8("x8"), t9("x9"), t10("x10"), t11("x11"), t12("x12"), t13("x13"), t14("x14"), t15("x15"), t16("x16"), t17("x17")
  )

  def withSqlCompiled[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_], T3 <: SqlTableDef[_], T4 <: SqlTableDef[_], T5 <: SqlTableDef[_], T6 <: SqlTableDef[_], T7 <: SqlTableDef[_], T8 <: SqlTableDef[_], T9 <: SqlTableDef[_], T10 <: SqlTableDef[_], T11 <: SqlTableDef[_], T12 <: SqlTableDef[_], T13 <: SqlTableDef[_], T14 <: SqlTableDef[_], T15 <: SqlTableDef[_], T16 <: SqlTableDef[_], T17 <: SqlTableDef[_]](t1: String => T1, t2: String => T2, t3: String => T3, t4: String => T4, t5: String => T5, t6: String => T6, t7: String => T7, t8: String => T8, t9: String => T9, t10: String => T10, t11: String => T11, t12: String => T12, t13: String => T13, t14: String => T14, t15: String => T15, t16: String => T16, t17: String => T17)(f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17) => U): U = memoize(
    withSql(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17)(f)
  )

  def withSql[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_], T3 <: SqlTableDef[_], T4 <: SqlTableDef[_], T5 <: SqlTableDef[_], T6 <: SqlTableDef[_], T7 <: SqlTableDef[_], T8 <: SqlTableDef[_], T9 <: SqlTableDef[_], T10 <: SqlTableDef[_], T11 <: SqlTableDef[_], T12 <: SqlTableDef[_], T13 <: SqlTableDef[_], T14 <: SqlTableDef[_], T15 <: SqlTableDef[_], T16 <: SqlTableDef[_], T17 <: SqlTableDef[_], T18 <: SqlTableDef[_]](t1: String => T1, t2: String => T2, t3: String => T3, t4: String => T4, t5: String => T5, t6: String => T6, t7: String => T7, t8: String => T8, t9: String => T9, t10: String => T10, t11: String => T11, t12: String => T12, t13: String => T13, t14: String => T14, t15: String => T15, t16: String => T16, t17: String => T17, t18: String => T18)(f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18) => U): U = f(
    t1("x1"), t2("x2"), t3("x3"), t4("x4"), t5("x5"), t6("x6"), t7("x7"), t8("x8"), t9("x9"), t10("x10"), t11("x11"), t12("x12"), t13("x13"), t14("x14"), t15("x15"), t16("x16"), t17("x17"), t18("x18")
  )

  def withSqlCompiled[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_], T3 <: SqlTableDef[_], T4 <: SqlTableDef[_], T5 <: SqlTableDef[_], T6 <: SqlTableDef[_], T7 <: SqlTableDef[_], T8 <: SqlTableDef[_], T9 <: SqlTableDef[_], T10 <: SqlTableDef[_], T11 <: SqlTableDef[_], T12 <: SqlTableDef[_], T13 <: SqlTableDef[_], T14 <: SqlTableDef[_], T15 <: SqlTableDef[_], T16 <: SqlTableDef[_], T17 <: SqlTableDef[_], T18 <: SqlTableDef[_]](t1: String => T1, t2: String => T2, t3: String => T3, t4: String => T4, t5: String => T5, t6: String => T6, t7: String => T7, t8: String => T8, t9: String => T9, t10: String => T10, t11: String => T11, t12: String => T12, t13: String => T13, t14: String => T14, t15: String => T15, t16: String => T16, t17: String => T17, t18: String => T18)(f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18) => U): U = memoize(
    withSql(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18)(f)
  )

  def withSql[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_], T3 <: SqlTableDef[_], T4 <: SqlTableDef[_], T5 <: SqlTableDef[_], T6 <: SqlTableDef[_], T7 <: SqlTableDef[_], T8 <: SqlTableDef[_], T9 <: SqlTableDef[_], T10 <: SqlTableDef[_], T11 <: SqlTableDef[_], T12 <: SqlTableDef[_], T13 <: SqlTableDef[_], T14 <: SqlTableDef[_], T15 <: SqlTableDef[_], T16 <: SqlTableDef[_], T17 <: SqlTableDef[_], T18 <: SqlTableDef[_], T19 <: SqlTableDef[_]](t1: String => T1, t2: String => T2, t3: String => T3, t4: String => T4, t5: String => T5, t6: String => T6, t7: String => T7, t8: String => T8, t9: String => T9, t10: String => T10, t11: String => T11, t12: String => T12, t13: String => T13, t14: String => T14, t15: String => T15, t16: String => T16, t17: String => T17, t18: String => T18, t19: String => T19)(f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19) => U): U = f(
    t1("x1"), t2("x2"), t3("x3"), t4("x4"), t5("x5"), t6("x6"), t7("x7"), t8("x8"), t9("x9"), t10("x10"), t11("x11"), t12("x12"), t13("x13"), t14("x14"), t15("x15"), t16("x16"), t17("x17"), t18("x18"), t19("x19")
  )

  def withSqlCompiled[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_], T3 <: SqlTableDef[_], T4 <: SqlTableDef[_], T5 <: SqlTableDef[_], T6 <: SqlTableDef[_], T7 <: SqlTableDef[_], T8 <: SqlTableDef[_], T9 <: SqlTableDef[_], T10 <: SqlTableDef[_], T11 <: SqlTableDef[_], T12 <: SqlTableDef[_], T13 <: SqlTableDef[_], T14 <: SqlTableDef[_], T15 <: SqlTableDef[_], T16 <: SqlTableDef[_], T17 <: SqlTableDef[_], T18 <: SqlTableDef[_], T19 <: SqlTableDef[_]](t1: String => T1, t2: String => T2, t3: String => T3, t4: String => T4, t5: String => T5, t6: String => T6, t7: String => T7, t8: String => T8, t9: String => T9, t10: String => T10, t11: String => T11, t12: String => T12, t13: String => T13, t14: String => T14, t15: String => T15, t16: String => T16, t17: String => T17, t18: String => T18, t19: String => T19)(f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19) => U): U = memoize(
    withSql(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19)(f)
  )

  def withSql[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_], T3 <: SqlTableDef[_], T4 <: SqlTableDef[_], T5 <: SqlTableDef[_], T6 <: SqlTableDef[_], T7 <: SqlTableDef[_], T8 <: SqlTableDef[_], T9 <: SqlTableDef[_], T10 <: SqlTableDef[_], T11 <: SqlTableDef[_], T12 <: SqlTableDef[_], T13 <: SqlTableDef[_], T14 <: SqlTableDef[_], T15 <: SqlTableDef[_], T16 <: SqlTableDef[_], T17 <: SqlTableDef[_], T18 <: SqlTableDef[_], T19 <: SqlTableDef[_], T20 <: SqlTableDef[_]](t1: String => T1, t2: String => T2, t3: String => T3, t4: String => T4, t5: String => T5, t6: String => T6, t7: String => T7, t8: String => T8, t9: String => T9, t10: String => T10, t11: String => T11, t12: String => T12, t13: String => T13, t14: String => T14, t15: String => T15, t16: String => T16, t17: String => T17, t18: String => T18, t19: String => T19, t20: String => T20)(f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20) => U): U = f(
    t1("x1"), t2("x2"), t3("x3"), t4("x4"), t5("x5"), t6("x6"), t7("x7"), t8("x8"), t9("x9"), t10("x10"), t11("x11"), t12("x12"), t13("x13"), t14("x14"), t15("x15"), t16("x16"), t17("x17"), t18("x18"), t19("x19"), t20("x20")
  )

  def withSqlCompiled[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_], T3 <: SqlTableDef[_], T4 <: SqlTableDef[_], T5 <: SqlTableDef[_], T6 <: SqlTableDef[_], T7 <: SqlTableDef[_], T8 <: SqlTableDef[_], T9 <: SqlTableDef[_], T10 <: SqlTableDef[_], T11 <: SqlTableDef[_], T12 <: SqlTableDef[_], T13 <: SqlTableDef[_], T14 <: SqlTableDef[_], T15 <: SqlTableDef[_], T16 <: SqlTableDef[_], T17 <: SqlTableDef[_], T18 <: SqlTableDef[_], T19 <: SqlTableDef[_], T20 <: SqlTableDef[_]](t1: String => T1, t2: String => T2, t3: String => T3, t4: String => T4, t5: String => T5, t6: String => T6, t7: String => T7, t8: String => T8, t9: String => T9, t10: String => T10, t11: String => T11, t12: String => T12, t13: String => T13, t14: String => T14, t15: String => T15, t16: String => T16, t17: String => T17, t18: String => T18, t19: String => T19, t20: String => T20)(f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20) => U): U = memoize(
    withSql(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20)(f)
  )

  def withSql[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_], T3 <: SqlTableDef[_], T4 <: SqlTableDef[_], T5 <: SqlTableDef[_], T6 <: SqlTableDef[_], T7 <: SqlTableDef[_], T8 <: SqlTableDef[_], T9 <: SqlTableDef[_], T10 <: SqlTableDef[_], T11 <: SqlTableDef[_], T12 <: SqlTableDef[_], T13 <: SqlTableDef[_], T14 <: SqlTableDef[_], T15 <: SqlTableDef[_], T16 <: SqlTableDef[_], T17 <: SqlTableDef[_], T18 <: SqlTableDef[_], T19 <: SqlTableDef[_], T20 <: SqlTableDef[_], T21 <: SqlTableDef[_]](t1: String => T1, t2: String => T2, t3: String => T3, t4: String => T4, t5: String => T5, t6: String => T6, t7: String => T7, t8: String => T8, t9: String => T9, t10: String => T10, t11: String => T11, t12: String => T12, t13: String => T13, t14: String => T14, t15: String => T15, t16: String => T16, t17: String => T17, t18: String => T18, t19: String => T19, t20: String => T20, t21: String => T21)(f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21) => U): U = f(
    t1("x1"), t2("x2"), t3("x3"), t4("x4"), t5("x5"), t6("x6"), t7("x7"), t8("x8"), t9("x9"), t10("x10"), t11("x11"), t12("x12"), t13("x13"), t14("x14"), t15("x15"), t16("x16"), t17("x17"), t18("x18"), t19("x19"), t20("x20"), t21("x21")
  )

  def withSqlCompiled[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_], T3 <: SqlTableDef[_], T4 <: SqlTableDef[_], T5 <: SqlTableDef[_], T6 <: SqlTableDef[_], T7 <: SqlTableDef[_], T8 <: SqlTableDef[_], T9 <: SqlTableDef[_], T10 <: SqlTableDef[_], T11 <: SqlTableDef[_], T12 <: SqlTableDef[_], T13 <: SqlTableDef[_], T14 <: SqlTableDef[_], T15 <: SqlTableDef[_], T16 <: SqlTableDef[_], T17 <: SqlTableDef[_], T18 <: SqlTableDef[_], T19 <: SqlTableDef[_], T20 <: SqlTableDef[_], T21 <: SqlTableDef[_]](t1: String => T1, t2: String => T2, t3: String => T3, t4: String => T4, t5: String => T5, t6: String => T6, t7: String => T7, t8: String => T8, t9: String => T9, t10: String => T10, t11: String => T11, t12: String => T12, t13: String => T13, t14: String => T14, t15: String => T15, t16: String => T16, t17: String => T17, t18: String => T18, t19: String => T19, t20: String => T20, t21: String => T21)(f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21) => U): U = memoize(
    withSql(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21)(f)
  )

  def withSql[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_], T3 <: SqlTableDef[_], T4 <: SqlTableDef[_], T5 <: SqlTableDef[_], T6 <: SqlTableDef[_], T7 <: SqlTableDef[_], T8 <: SqlTableDef[_], T9 <: SqlTableDef[_], T10 <: SqlTableDef[_], T11 <: SqlTableDef[_], T12 <: SqlTableDef[_], T13 <: SqlTableDef[_], T14 <: SqlTableDef[_], T15 <: SqlTableDef[_], T16 <: SqlTableDef[_], T17 <: SqlTableDef[_], T18 <: SqlTableDef[_], T19 <: SqlTableDef[_], T20 <: SqlTableDef[_], T21 <: SqlTableDef[_], T22 <: SqlTableDef[_]](t1: String => T1, t2: String => T2, t3: String => T3, t4: String => T4, t5: String => T5, t6: String => T6, t7: String => T7, t8: String => T8, t9: String => T9, t10: String => T10, t11: String => T11, t12: String => T12, t13: String => T13, t14: String => T14, t15: String => T15, t16: String => T16, t17: String => T17, t18: String => T18, t19: String => T19, t20: String => T20, t21: String => T21, t22: String => T22)(f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22) => U): U = f(
    t1("x1"), t2("x2"), t3("x3"), t4("x4"), t5("x5"), t6("x6"), t7("x7"), t8("x8"), t9("x9"), t10("x10"), t11("x11"), t12("x12"), t13("x13"), t14("x14"), t15("x15"), t16("x16"), t17("x17"), t18("x18"), t19("x19"), t20("x20"), t21("x21"), t22("x22")
  )

  def withSqlCompiled[U, T1 <: SqlTableDef[_], T2 <: SqlTableDef[_], T3 <: SqlTableDef[_], T4 <: SqlTableDef[_], T5 <: SqlTableDef[_], T6 <: SqlTableDef[_], T7 <: SqlTableDef[_], T8 <: SqlTableDef[_], T9 <: SqlTableDef[_], T10 <: SqlTableDef[_], T11 <: SqlTableDef[_], T12 <: SqlTableDef[_], T13 <: SqlTableDef[_], T14 <: SqlTableDef[_], T15 <: SqlTableDef[_], T16 <: SqlTableDef[_], T17 <: SqlTableDef[_], T18 <: SqlTableDef[_], T19 <: SqlTableDef[_], T20 <: SqlTableDef[_], T21 <: SqlTableDef[_], T22 <: SqlTableDef[_]](t1: String => T1, t2: String => T2, t3: String => T3, t4: String => T4, t5: String => T5, t6: String => T6, t7: String => T7, t8: String => T8, t9: String => T9, t10: String => T10, t11: String => T11, t12: String => T12, t13: String => T13, t14: String => T14, t15: String => T15, t16: String => T16, t17: String => T17, t18: String => T18, t19: String => T19, t20: String => T20, t21: String => T21, t22: String => T22)(f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22) => U): U = memoize(
    withSql(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22)(f)
  )


  import java.util.concurrent.ConcurrentHashMap
  import scala.collection._

  val cache: concurrent.Map[String, Any] = new ConcurrentHashMap[String, Any]().asScala

  private def memoize[U](f: => U): U = {

    val key = StackWalker
      .getInstance(java.lang.StackWalker.Option.RETAIN_CLASS_REFERENCE)
      .walk(frames => frames.skip(2).findFirst()).map(_.toStackTraceElement).toScala
      .map(el => s"${el.getClassName}.${el.getMethodName}:${el.getLineNumber}").getOrElse("")

    cache.getOrElseUpdate(
      key, f
    ).asInstanceOf[U]
  }
}
