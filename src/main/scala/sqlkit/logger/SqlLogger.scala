package sqlkit.logger


import sqlkit.query.SqlQueryCommon
import sqlkit.session.SqlSession

trait SqlLogger {

  def log(query: SqlQueryCommon[_], duration: Option[Long], rows: Option[Long])(implicit session: SqlSession)

  def get(uuid:String): List[SqlQueryLog]
}
