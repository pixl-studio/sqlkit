package sqlkit

import java.util.UUID

import sqlkit.param.{SqlParam, SqlParamValue, SqlParams}

private[this] object NoopArg
private[this] case class SqlUnsafe(value: String)

class SqlInterpolation(val sc: StringContext) extends AnyVal {

  def sql(args: Any*) = /*time("SqlStringInterpolation")*/ {

    val (query, params) = sc.parts.zipAll(args, "", NoopArg).foldLeft(new StringBuilder(), List.empty[SqlParam]) { case ((sb, params), (p, a)) =>

      sb.append(p)
      val key = UUID.randomUUID().toString.replaceAll("-", "_")

      val newParam = a match {
        case NoopArg => None
        case col: SqlColumn[_] => {
          sb.append(col.toSqlString)
          None
        }
        case col: List[SqlColumn[_]] @unchecked => {
          sb.append(col.map(_.toSqlString).mkString(", "))
          None
        }
        case cols: SqlColumns => {
          sb.append(cols.columns.map { case (c, _) =>
            s"${c.toSqlString} = :${c.toSqlParam}"
          }.mkString(cols.mkString))
          Some(cols.columns.map{ case (c, v) => SqlParam(c.toSqlParam, v)})
        }
        case p: SqlParams => {
          sb.append(p.params.map { p =>
            s":${p.name}"
          }.mkString(","))
          Some(p.params)
        }
        case tableDef: SqlTableDef[_] @unchecked => {
          sb.append(tableDef.toSqlString)
          None
        }
        case s: Option[SqlQuery[_]] => {
          s.map { q =>
            sb.append(q.rawQuery)
            q.params
          }
        }
        case s: SqlUnsafe => {
          sb.append(s.value)
          None
        }
        case p => {
          sb.append(s":$key")
          Some(List(SqlParam(key, SqlParamValue.wrap(p))))
        }
      }

      (sb, params ++ newParam.getOrElse(Nil))
    }

    val rawQuery = query.toString

    var paramsIndexes = List.empty[String]
    val queryWithParams = SqlSettings.namedParameterRegex.replaceAllIn(rawQuery, m => {
      paramsIndexes :+= m.group(1)
      "?"
    })

    SqlQuery(
      query = queryWithParams,
      rawQuery = rawQuery,
      params = params,
      paramsIndexes = paramsIndexes,
      fromSql = rs => ()
    )
  }
}