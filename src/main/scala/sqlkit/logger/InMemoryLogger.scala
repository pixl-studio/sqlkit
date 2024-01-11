package sqlkit.logger
import sqlkit.query.SqlQueryCommon
import sqlkit.session.SqlSession
import sqlkit.utils.LRUCache

class InMemoryLogger(
  maxSize:Int=50
) extends SqlLogger {

  private val cache = LRUCache[String, List[SqlQueryLog]](maxSize)

  override def log(query: SqlQueryCommon[_], time: Option[Long], rows: Option[Long])(implicit session: SqlSession): Unit = {

    val q = SqlQueryLog(
      session.uuid, query.uuid, query.debugSql, time, rows
    )

    cache.put(session.uuid, cache.getOrElse(session.uuid, Nil) :+ q)

  }

  def get(uuid:String): List[SqlQueryLog] = {
    cache.getOrElse(uuid, Nil)
  }
}
