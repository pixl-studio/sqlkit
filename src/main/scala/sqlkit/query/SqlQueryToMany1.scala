package sqlkit.query

import sqlkit.SqlRow
import sqlkit.param.SqlParam
import sqlkit.session.SqlSession

import scala.util.Try

case class SqlQueryToMany1[R, R1](
  query: String,
  rawQuery: String = "",
  params: List[SqlParam] = Nil,
  paramsIndexes: List[String] = Nil,
  one: SqlRow => R,
  toMany1: SqlRow => R1
) extends SqlQueryCommon[SqlRow] {

  val fromSql = (row: SqlRow) => row

  def on(params: SqlParam*): SqlQueryToMany1[R, R1] = {
    this.copy(params = this.params ++ params.toList)
  }

  def list(implicit session: SqlSession) = {
    exec { rs =>
      rs.foldLeft(Map.empty[R, List[R1]]) { case (all, row) =>
        val o = one(row)
        val item = all.getOrElse(o, List.empty[R1])
        all + (
          o -> (
            item ++ Try { toMany1(row) }.toOption
          )
        )
      }.toList
    }
  }

  def listSets(implicit session: SqlSession) = {
    list.map { case (k, toMany1) =>
      (k, Set.from(toMany1))
    }
  }
}
