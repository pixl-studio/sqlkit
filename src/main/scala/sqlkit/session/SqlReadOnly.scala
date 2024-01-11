package sqlkit.session

import java.sql.Connection
import sqlkit.DB

import java.util.UUID

case class SqlReadOnly(
  dataSource: String = DB.defaultSource,
  schema:Option[String]=None,
  uuid:String= UUID.randomUUID().toString
) extends SqlSession {

  override protected def getConnection: Connection = {
    val connection = DB.dataSource(dataSource).getConnection
    connection.setReadOnly(true)
    connection
  }
}
