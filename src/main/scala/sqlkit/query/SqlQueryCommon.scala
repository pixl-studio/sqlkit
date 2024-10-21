package sqlkit.query

import sqlkit.logger.PreparedStatementLogger
import sqlkit.param.SqlParam
import sqlkit.session.SqlSession
import sqlkit.utils.Timing
import sqlkit.{DB, SqlResult, SqlResultSet, SqlRow}

import java.sql.{PreparedStatement, Statement}
import java.time.LocalDateTime
import java.util.UUID

trait SqlQueryCommon[T] {

  val uuid: String = UUID.randomUUID().toString

  val query: String
  val rawQuery: String
  val params: List[SqlParam]
  val paramsIndexes: List[String]
  val fromSql: SqlRow => T

  def oneToMany[U, U1](
    one: SqlRow => U,
    toMany1: SqlRow => U1
  ) = {
    SqlQueryToMany1(
      query, rawQuery, params, paramsIndexes, one, toMany1
    )
  }

  def oneToMany[U, U1, U2](
    one: SqlRow => U,
    toMany1: SqlRow => U1,
    toMany2: SqlRow => U2
  ) = {
    SqlQueryToMany2(
      query, rawQuery, params, paramsIndexes, one, toMany1, toMany2
    )
  }

  def oneToMany[U, U1, U2, U3](
    one: SqlRow => U,
    toMany1: SqlRow => U1,
    toMany2: SqlRow => U2,
    toMany3: SqlRow => U3
  ) = {
    SqlQueryToMany3(
      query, rawQuery, params, paramsIndexes, one, toMany1, toMany2, toMany3
    )
  }

  def oneToMany[U, U1, U2, U3, U4](
    one: SqlRow => U,
    toMany1: SqlRow => U1,
    toMany2: SqlRow => U2,
    toMany3: SqlRow => U3,
    toMany4: SqlRow => U4
  ) = {
    SqlQueryToMany4(
      query, rawQuery, params, paramsIndexes, one, toMany1, toMany2, toMany3, toMany4
    )
  }

  def oneToMany[U, U1, U2, U3, U4, U5](
    one: SqlRow => U,
    toMany1: SqlRow => U1,
    toMany2: SqlRow => U2,
    toMany3: SqlRow => U3,
    toMany4: SqlRow => U4,
    toMany5: SqlRow => U5
  ) = {
    SqlQueryToMany5(
      query, rawQuery, params, paramsIndexes, one, toMany1, toMany2, toMany3, toMany4, toMany5
    )
  }

  protected def exec[R](f: SqlResultSet[T] => R)(implicit session: SqlSession): R = {

    session.withConnection { connection =>

      connection.setSchema(session.schema.orNull)

      val ps: PreparedStatement = connection.prepareStatement(query)

      paramsIndexes.zip(LazyList from (1)).foreach { case (name, index) =>
        val namedParameter = params.find(_.name == name).getOrElse(throw new Exception(s"param :${name} is not defined in prepareStatement"))
        namedParameter.set(ps, index)
      }

      val (rawResultSet, duration) = Timing.traceOpt(ps.executeQuery(), DB.shouldLog(session.dataSource))

      val rs = new SqlResultSet[T](rawResultSet, fromSql)
      val result = f(rs)

      DB.log(this, duration, Some(rs.getRows()))

      result
    }

  }

  def exec[K](implicit session: SqlSession): SqlResult[K] = {

    session.withConnection { connection =>

      connection.setSchema(session.schema.orNull)

      val ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)

      paramsIndexes.zip(LazyList from (1)).foreach { case (name, index) =>
        val namedParameter = params.find(_.name == name).getOrElse(throw new Exception(s"param :${name} is not defined in preparareStatement"))
        namedParameter.set(ps, index)
      }

      val (affectedRows, duration) = Timing.traceOpt(ps.executeUpdate(), DB.shouldLog(session.dataSource))
      DB.log(this, duration, None)

      val rs = ps.getGeneratedKeys
      var keys = List.empty[K]
      while (rs.next()){
        keys :+= rs.getLong(1).asInstanceOf[K]
      }

      SqlResult(
        affectedRows, keys
      )
    }
  }

  def execBatch[K, T](entities: List[List[SqlParam]], batchSize:Int)(implicit session: SqlSession): SqlResult[K] = {

    session.withConnection { connection =>

      connection.setSchema(session.schema.orNull)

      val results = entities.grouped(batchSize).map { entitiesGroup =>

        val ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        entitiesGroup.foreach { params =>
          paramsIndexes.zip(LazyList from (1)).foreach { case (name, index) =>
            val namedParameter = params.find(_.name == name).getOrElse(
              throw new Exception(s"param :${name} is not defined in preparareStatement")
            )
            namedParameter.set(ps, index)
          }
          ps.addBatch()
        }

        val (affectedRows, duration) = Timing.traceOpt(ps.executeBatch(), DB.shouldLog(session.dataSource))
        DB.log(this, duration, None)

        val rs = ps.getGeneratedKeys
        var keys = List.empty[K]
        while (rs.next()) {
          keys :+= rs.getLong(1).asInstanceOf[K]
        }

        ps.close()

        SqlResult[K](
          affectedRows.sum, keys
        )
      }

      results.reduce( (a, b) =>
        a.copy(
          affectedRows = a.affectedRows + b.affectedRows,
          generatedKeys = a.generatedKeys ++ b.generatedKeys
        )
      )
    }

  }

  def isEmpty = {
    this.query.trim.isEmpty
  }

  lazy val debugSql = {
    var i = 0
    val ps = new PreparedStatementLogger()
    """\?""".r.replaceAllIn(query, m => {
      val paramSql = for {
        name <- paramsIndexes.lift(i)
        param <- params.find(_.name == name)
      } yield {
        param.set(ps, i)
        ps.value
      }
      i+=1
      paramSql.getOrElse("")
    })
  }
}
