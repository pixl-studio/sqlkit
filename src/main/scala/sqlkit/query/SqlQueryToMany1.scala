package sqlkit.query

import sqlkit.SqlRow
import sqlkit.param.SqlParam
import sqlkit.session.SqlSession

import scala.collection.immutable.VectorMap
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

  def list(implicit session: SqlSession): List[(R, List[R1])] = {
    exec { rs =>
      rs.foldLeft(VectorMap.empty[R, List[R1]]) { case (all, row) =>
        val o = one(row)
        val item = all.getOrElse(o, List.empty[R1])

        all.updated(o, item ++ Try { toMany1(row) }.toOption)
      }.toList
    }
  }

  def listSets(implicit session: SqlSession): List[(R, Set[R1])] = {
    list.map { case (k, toMany1) =>
      (k, Set.from(toMany1))
    }
  }
}
