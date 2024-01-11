package sqlkit.query

import sqlkit.SqlRow
import sqlkit.param.SqlParam
import sqlkit.session.SqlSession

import scala.util.Try

case class SqlQueryToMany5[R, R1, R2, R3, R4, R5](
  query: String,
  rawQuery: String = "",
  params: List[SqlParam] = Nil,
  paramsIndexes: List[String] = Nil,
  one: SqlRow => R,
  toMany1: SqlRow => R1,
  toMany2: SqlRow => R2,
  toMany3: SqlRow => R3,
  toMany4: SqlRow => R4,
  toMany5: SqlRow => R5
) extends SqlQueryCommon[SqlRow] {

  val fromSql = (row: SqlRow) => row

  def on(params: SqlParam*): SqlQueryToMany5[R, R1, R2, R3, R4, R5] = {
    this.copy(params = this.params ++ params.toList)
  }

  def list(implicit session: SqlSession) = {
    exec { rs =>
      rs.foldLeft(Map.empty[R, (List[R1], List[R2], List[R3], List[R4], List[R5])]) { case (all, row) =>
        val o = one(row)
        val (item1, item2, item3, item4, item5) = all.getOrElse(o, (List.empty[R1], List.empty[R2], List.empty[R3], List.empty[R4], List.empty[R5]))
        all + (
          o -> ((
            item1 ++ Try { toMany1(row) }.toOption,
            item2 ++ Try { toMany2(row) }.toOption,
            item3 ++ Try { toMany3(row) }.toOption,
            item4 ++ Try { toMany4(row) }.toOption,
            item5 ++ Try { toMany5(row) }.toOption,
          ))
        )
      }.toList
    }
  }

  def listSets(implicit session: SqlSession) = {
    list.map { case (k, (toMany1, toMany2, toMany3, toMany4, toMany5)) =>
      (k, Set.from(toMany1), Set.from(toMany2), Set.from(toMany3), Set.from(toMany4), Set.from(toMany5))
    }
  }
}
