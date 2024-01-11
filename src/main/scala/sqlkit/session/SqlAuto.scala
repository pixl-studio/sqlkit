package sqlkit.session

import sqlkit.DB

import java.sql.Connection
import java.util.UUID

case class SqlAuto(
  dataSource: String = DB.defaultSource,
  schema: Option[String] = None,
  uuid:String= UUID.randomUUID().toString
) extends SqlSession {

  protected def getConnection: Connection = {
    DB.dataSource(dataSource).getConnection
  }
}