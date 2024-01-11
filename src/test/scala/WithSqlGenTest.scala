import org.scalatest.funsuite.AnyFunSuite

class WithSqlGenTest extends AnyFunSuite{

  (1 to 22).map { i =>
    val t = (1 to i).map(n => s"T$n<:SqlTableDef[_]").mkString(", ")
    val args = (1 to i).map(n => s"t$n:String => T$n").mkString(", ")
    val f = (1 to i).map(n => s"T$n").mkString(", ")
    //val alias = (1 to i).map(n => s"""t$n.withAlias("x$n").asInstanceOf[T$n]""").mkString(",\n\t")
    val alias = (1 to i).map(n => s"""t$n("x$n")""").mkString(", ")
    val aliasCompiled = (1 to i).map(n => s"""t$n""").mkString(", ")

    println(
      s"""
         |def withSql[U, ${t}](${args})(f: ($f) => U): U = f(
         |  $alias
         |)""".
        stripMargin)

    println(
      s"""def withSqlCompiled[U, ${t}](${args})(f: ($f) => U): U = memoize(
         |  withSql(${aliasCompiled})(f)
         |)""".
        stripMargin)
  }
}
