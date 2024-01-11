package sqlkit.query

import sqlkit.SqlRow
import sqlkit.param.SqlParam
import sqlkit.session.SqlSession

import scala.util.Try

case class SqlQueryToMany2[R, R1, R2](
  query: String,
  rawQuery: String = "",
  params: List[SqlParam] = Nil,
  paramsIndexes: List[String] = Nil,
  one: SqlRow => R,
  toMany1: SqlRow => R1,
  toMany2: SqlRow => R2
) extends SqlQueryCommon[SqlRow] {

  val fromSql = (row: SqlRow) => row

  def on(params: SqlParam*): SqlQueryToMany2[R, R1, R2] = {
    this.copy(params = this.params ++ params.toList)
  }

  def list(implicit session: SqlSession) = {
    exec { rs =>
      rs.foldLeft(Map.empty[R, (List[R1], List[R2])]) { case (all, row) =>
        val o = one(row)
        val (item1, item2) = all.getOrElse(o, (List.empty[R1], List.empty[R2]))
        all + (
          o -> ((
            item1 ++ Try { toMany1(row) }.toOption,
            item2 ++ Try { toMany2(row) }.toOption,
          ))
        )
      }.toList
    }
  }

  def listSets(implicit session: SqlSession) = {
    list.map { case (k, (toMany1, toMany2)) =>
      (k, Set.from(toMany1), Set.from(toMany2))
    }
  }
}
