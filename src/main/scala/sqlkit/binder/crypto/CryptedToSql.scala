package sqlkit.binder.crypto

import sqlkit.binder.SqlBinder

import java.sql.PreparedStatement

case class CryptedToSql[T](
  crypo: Crypto
)(implicit underlying: SqlBinder[T]) extends SqlBinder[T] {

  override def fromSql(o: Object): T = {
    underlying.unwrap(crypo.decrypt(o.toString))
  }

  override def toSql(v: T, statement: PreparedStatement, index: Int): Unit = {
    statement.setString(index, crypo.encrypt(underlying.wrap(v)))
  }

  override def unwrap(o: String): T = underlying.unwrap(crypo.decrypt(o))
}