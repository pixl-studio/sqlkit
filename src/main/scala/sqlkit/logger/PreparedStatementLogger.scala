package sqlkit.logger

import java.io.{InputStream, Reader}
import java.net.URL
import java.sql
import java.sql.{Blob, Clob, Connection, Date, NClob, ParameterMetaData, PreparedStatement, Ref, ResultSet, ResultSetMetaData, RowId, SQLWarning, SQLXML, Time, Timestamp}
import java.util.Calendar

class PreparedStatementLogger extends PreparedStatement {

  var value = "?"

  private def escapeQuote(str:String) = {
    s"""'${str.replaceAll("'", "''")}'"""
  }

  override def setNull(parameterIndex: Int, sqlType: Int): Unit = {
    this.value = "NULL"
  }

  override def setBoolean(parameterIndex: Int, x: Boolean): Unit = {
    this.value = x.toString
  }

  override def setByte(parameterIndex: Int, x: Byte): Unit = {
    this.value = x.toString
  }

  override def setShort(parameterIndex: Int, x: Short): Unit = {
    this.value = x.toString
  }

  override def setInt(parameterIndex: Int, x: Int): Unit = {
    this.value = x.toString
  }

  override def setLong(parameterIndex: Int, x: Long): Unit = {
    this.value = x.toString
  }

  override def setFloat(parameterIndex: Int, x: Float): Unit = {
    this.value = x.toString
  }

  override def setDouble(parameterIndex: Int, x: Double): Unit = {
    this.value = x.toString
  }

  override def setBigDecimal(parameterIndex: Int, x: java.math.BigDecimal): Unit = {
    this.value = x.toString
  }

  override def setString(parameterIndex: Int, x: String): Unit = {
    this.value = escapeQuote(x)
  }

  override def setBytes(parameterIndex: Int, x: Array[Byte]): Unit = {
    this.value = x.toString
  }

  override def setDate(parameterIndex: Int, x: Date): Unit = {
    this.value = escapeQuote(x.toString)
  }

  override def setTime(parameterIndex: Int, x: Time): Unit = {
    this.value = escapeQuote(x.toString)
  }

  override def setTimestamp(parameterIndex: Int, x: Timestamp): Unit = {
    this.value = x.toString
  }

  override def setAsciiStream(parameterIndex: Int, x: InputStream, length: Int): Unit = { 
    this.value = "?" 
  }

  override def setUnicodeStream(parameterIndex: Int, x: InputStream, length: Int): Unit = { 
    this.value = "?" 
  }

  override def setBinaryStream(parameterIndex: Int, x: InputStream, length: Int): Unit = { 
    this.value = "?" 
  }

  override def setObject(parameterIndex: Int, x: Any, targetSqlType: Int): Unit = {
    this.value = x.toString
  }

  override def setObject(parameterIndex: Int, x: Any): Unit = {
    this.value = x.toString
  }

  override def setCharacterStream(parameterIndex: Int, reader: Reader, length: Int): Unit = { 
    this.value = "?" 
  }

  override def setRef(parameterIndex: Int, x: Ref): Unit = { 
    this.value = "?" 
  }

  override def setBlob(parameterIndex: Int, x: Blob): Unit = { 
    this.value = "?" 
  }

  override def setClob(parameterIndex: Int, x: Clob): Unit = { 
    this.value = "?" 
  }

  override def setArray(parameterIndex: Int, x: sql.Array): Unit = { 
    this.value = "?" 
  }

  override def setDate(parameterIndex: Int, x: Date, cal: Calendar): Unit = { 
    this.value = "?" 
  }

  override def setTime(parameterIndex: Int, x: Time, cal: Calendar): Unit = { 
    this.value = "?" 
  }

  override def setTimestamp(parameterIndex: Int, x: Timestamp, cal: Calendar): Unit = { 
    this.value = "?" 
  }

  override def setNull(parameterIndex: Int, sqlType: Int, typeName: String): Unit = { 
    this.value = "?" 
  }

  override def setURL(parameterIndex: Int, x: URL): Unit = { 
    this.value = "?" 
  }

  override def setRowId(parameterIndex: Int, x: RowId): Unit = { 
    this.value = "?" 
  }

  override def setNString(parameterIndex: Int, value: String): Unit = { 
    this.value = "?" 
  }

  override def setNCharacterStream(parameterIndex: Int, value: Reader, length: Long): Unit = { 
    this.value = "?" 
  }

  override def setNClob(parameterIndex: Int, value: NClob): Unit = { 
    this.value = "?" 
  }

  override def setClob(parameterIndex: Int, reader: Reader, length: Long): Unit = { 
    this.value = "?" 
  }

  override def setBlob(parameterIndex: Int, inputStream: InputStream, length: Long): Unit = { 
    this.value = "?" 
  }

  override def setNClob(parameterIndex: Int, reader: Reader, length: Long): Unit = { 
    this.value = "?" 
  }

  override def setSQLXML(parameterIndex: Int, xmlObject: SQLXML): Unit = { 
    this.value = "?" 
  }

  override def setObject(parameterIndex: Int, x: Any, targetSqlType: Int, scaleOrLength: Int): Unit = { 
    this.value = "?" 
  }

  override def setAsciiStream(parameterIndex: Int, x: InputStream, length: Long): Unit = { 
    this.value = "?" 
  }

  override def setBinaryStream(parameterIndex: Int, x: InputStream, length: Long): Unit = { 
    this.value = "?" 
  }

  override def setCharacterStream(parameterIndex: Int, reader: Reader, length: Long): Unit = { 
    this.value = "?" 
  }

  override def setAsciiStream(parameterIndex: Int, x: InputStream): Unit = { 
    this.value = "?" 
  }

  override def setBinaryStream(parameterIndex: Int, x: InputStream): Unit = { 
    this.value = "?" 
  }

  override def setCharacterStream(parameterIndex: Int, reader: Reader): Unit = { 
    this.value = "?" 
  }

  override def setNCharacterStream(parameterIndex: Int, value: Reader): Unit = { 
    this.value = "?" 
  }

  override def setClob(parameterIndex: Int, reader: Reader): Unit = { 
    this.value = "?" 
  }

  override def setBlob(parameterIndex: Int, inputStream: InputStream): Unit = { 
    this.value = "?" 
  }

  override def setNClob(parameterIndex: Int, reader: Reader): Unit = { 
    this.value = "?" 
  }

  // ----

  override def getMetaData: ResultSetMetaData = ???

  override def executeQuery(): ResultSet = ???

  override def executeUpdate(): Int = ???

  override def clearParameters(): Unit = ???

  override def execute(): Boolean = ???

  override def addBatch(): Unit = ???

  override def getParameterMetaData: ParameterMetaData = ???

  override def executeQuery(sql: String): ResultSet = ???

  override def executeUpdate(sql: String): Int = ???

  override def close(): Unit = ???

  override def getMaxFieldSize: Int = ???

  override def setMaxFieldSize(max: Int): Unit = ???

  override def getMaxRows: Int = ???

  override def setMaxRows(max: Int): Unit = ???

  override def setEscapeProcessing(enable: Boolean): Unit = ???

  override def getQueryTimeout: Int = ???

  override def setQueryTimeout(seconds: Int): Unit = ???

  override def cancel(): Unit = ???

  override def getWarnings: SQLWarning = ???

  override def clearWarnings(): Unit = ???

  override def setCursorName(name: String): Unit = ???

  override def execute(sql: String): Boolean = ???

  override def getResultSet: ResultSet = ???

  override def getUpdateCount: Int = ???

  override def getMoreResults: Boolean = ???

  override def setFetchDirection(direction: Int): Unit = ???

  override def getFetchDirection: Int = ???

  override def setFetchSize(rows: Int): Unit = ???

  override def getFetchSize: Int = ???

  override def getResultSetConcurrency: Int = ???

  override def getResultSetType: Int = ???

  override def addBatch(sql: String): Unit = ???

  override def clearBatch(): Unit = ???

  override def executeBatch(): Array[Int] = ???

  override def getConnection: Connection = ???

  override def getMoreResults(current: Int): Boolean = ???

  override def getGeneratedKeys: ResultSet = ???

  override def executeUpdate(sql: String, autoGeneratedKeys: Int): Int = ???

  override def executeUpdate(sql: String, columnIndexes: Array[Int]): Int = ???

  override def executeUpdate(sql: String, columnNames: Array[String]): Int = ???

  override def execute(sql: String, autoGeneratedKeys: Int): Boolean = ???

  override def execute(sql: String, columnIndexes: Array[Int]): Boolean = ???

  override def execute(sql: String, columnNames: Array[String]): Boolean = ???

  override def getResultSetHoldability: Int = ???

  override def isClosed: Boolean = ???

  override def setPoolable(poolable: Boolean): Unit = ???

  override def isPoolable: Boolean = ???

  override def closeOnCompletion(): Unit = ???

  override def isCloseOnCompletion: Boolean = ???

  override def unwrap[T](iface: Class[T]): T = ???

  override def isWrapperFor(iface: Class[_]): Boolean = ???
}
