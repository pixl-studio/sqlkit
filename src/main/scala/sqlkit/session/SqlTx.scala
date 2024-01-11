package sqlkit.session

import java.sql.Connection._
import java.sql.{Connection, SQLException}
import sqlkit.{DB, SqlSettings}

import java.util.UUID
import scala.util.control.Exception._
import scala.util.control.NonFatal

/**
  * DB Transaction abstraction.
  * @param conn connection
  */
case class SqlTx(
  dataSource: String = DB.defaultSource,
  schema:Option[String]=None,
  isolationLevel: IsolationLevel = IsolationLevel.Default,
  uuid:String= UUID.randomUUID().toString
) extends SqlSession {

  private lazy val connection = getConnection

  override protected def getConnection: Connection = {
    DB.dataSource(dataSource).getConnection
  }

  private[this] def setTransactionIsolation(): Unit = {
    // Set isolation level for the transaction
    isolationLevel match {
      case IsolationLevel.Serializable =>
        connection.setTransactionIsolation(TRANSACTION_SERIALIZABLE)
      case IsolationLevel.RepeatableRead =>
        connection.setTransactionIsolation(TRANSACTION_REPEATABLE_READ)
      case IsolationLevel.ReadCommitted =>
        connection.setTransactionIsolation(TRANSACTION_READ_COMMITTED)
      case IsolationLevel.ReadUncommitted =>
        connection.setTransactionIsolation(TRANSACTION_READ_UNCOMMITTED)
      case IsolationLevel.Default =>
      // Do nothing
    }
  }

  /**
    * Begins this transaction.
    */
  def begin(): Unit = {
    setTransactionIsolation()
    connection.setAutoCommit(false)
    if (!SqlSettings.jtaDataSourceCompatible) {
      connection.setReadOnly(false)
    }
  }

  /**
    * Commits this transaction.
    */
  def commit(): Unit = {
    try connection.commit() catch {
      case NonFatal(e) =>
        try connection.rollback() catch {
          case NonFatal(e2) => e.addSuppressed(e2)
        }
        throw e
    }
    connection.setAutoCommit(true)
  }

  /**
    * Rolls this transaction back.
    */
  def rollback(): Unit = {
    connection.rollback()
  }

  override def withConnection[T](f: Connection => T): T = {
    f(connection)
  }

}

object SqlTx {

  def localTx[T](dataSource: String = DB.defaultSource,
    schema: Option[String] = None,
    isolationLevel: IsolationLevel = IsolationLevel.Default
  )(f: SqlTx => T): T = {

    val tx = SqlTx(dataSource, schema, isolationLevel)

    try {
      tx.begin()
      val result = f(tx)
      tx.commit()
      result
    } catch {
      case t: Throwable => {
        tx.rollback()
        throw new Exception("Auto rollback transaction", t)
      }
    } finally {
      tx.close()
    }
  }
}

