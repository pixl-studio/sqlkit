package sqlkit

import sqlkit.exceptions.TooManyRowsException
import sqlkit.param.SqlParam
import sqlkit.query.SqlQueryCommon
import sqlkit.session.SqlSession

import scala.concurrent.{ExecutionContext, Future}

case class SqlQuery[T](
  query: String,
  rawQuery: String = "",
  params: List[SqlParam] = Nil,
  paramsIndexes: List[String] = Nil,
  fromSql: SqlRow => T
) extends SqlQueryCommon[T] {

  def as[U](tableDef: SqlTableDef[U]): SqlQuery[U] = {
    as[U](tableDef.fromSql _)
  }

  def as[U](f: SqlRow => U): SqlQuery[U] = {
    this.copy(fromSql = f)
      .asInstanceOf[SqlQuery[U]]
  }

  def on(params: SqlParam*): SqlQuery[T] = {
    this.copy(params = this.params ++ params.toList)
  }

  def single(implicit session: SqlSession): Option[T] = {
    exec { rs =>
      val result = rs.nextOption
      if(rs.hasNext){
        throw TooManyRowsException("Too many rows (more than 1) were returned from query")
      }
      result
    }
  }

  def first(implicit session: SqlSession): Option[T] = {
    exec { rs =>
      rs.nextOption
    }
  }

  def list(implicit session: SqlSession): List[T] = {
    exec { rs =>
      rs.toList
    }
  }

  /**
    * Automatic pagination.
    * Fire 2 requests:
    * One for the count keeping from, where, ...
    * and the other for sublist by auto inject LIMIT CLAUSE.
    *
    * @param offset
    * @param limit
    * @param session
    * @return
    */
  def list(offset: Long, limit: Long)(implicit session: SqlSession): SqlList[T] = {
    list(Some(offset), Some(limit))
  }

  def list(offset: Option[Long], limit: Option[Long])(implicit session: SqlSession): SqlList[T] = {

    val countSql = this.copy(
      query = "(?si)SELECT.*FROM".r.replaceFirstIn(this.query, "SELECT COUNT(*) FROM")
    ).as(row => row.get[Long](1))

    val count = countSql.exec { rs => rs.nextOption } getOrElse 0L

    val xs = this.copy(
      query = this.query + limit.map(l => s" LIMIT $l").getOrElse("") + offset.map(o => s" OFFSET $o").getOrElse("")
    ).list

    SqlList[T](xs, count=count, offset=offset, limit=limit)
  }

  def singleF(implicit session: SqlSession, ec: ExecutionContext): Future[Option[T]] = {
    Future(single)
  }

  def listF(implicit session: SqlSession, ec: ExecutionContext): Future[List[T]] = {
    Future(list)
  }

  def listF(offset: Long, limit: Long)(implicit session: SqlSession, ec: ExecutionContext): Future[SqlList[T]] = {
    Future(list(offset, limit))
  }

  def ++(q: String): SqlQuery[T] = {
    this.copy(
      query = query + q,
      rawQuery = rawQuery + q
    )
  }

  def ++(q: SqlQuery[T]): SqlQuery[T] = {
    this.copy(
      query = query + q.query,
      rawQuery = rawQuery + q.rawQuery,
      params = params ++ q.params,
      paramsIndexes = paramsIndexes ++ q.paramsIndexes
    )
  }
}