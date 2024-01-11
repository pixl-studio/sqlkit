package sqlkit.session

import java.sql.Connection
import org.slf4j.LoggerFactory
import sqlkit.DB

import java.util.UUID
import scala.util.control.Exception.ignoring
import scala.util.control.NonFatal

trait SqlSession extends AutoCloseable {

  val uuid: String

  val schema:Option[String]

  protected def getConnection : Connection

  private var connection: Connection = null

  val logger = LoggerFactory.getLogger("sqlkit")

  def withConnection[T](f: Connection => T): T = {
    try {
      connection = getConnection
      f(connection)
    } finally {
      try {
        close()
      } catch {
        case NonFatal(e) =>
          logger.warn(s"Failed to close resource ${connection.getClass.getName} (error: ${e.getMessage})")
      }
    }
  }

  /**
    * Close the connection.
    */
  def close(): Unit = {
    ignoring(classOf[Throwable]) {
      connection.close()
    }
  }
}
object SqlSession {

  def auto(
    dataSource: String = DB.defaultSource,
    schema: Option[String] = None,
    uuid:String= UUID.randomUUID().toString
  ): SqlSession = {
    SqlAuto(dataSource, schema, uuid)
  }

}