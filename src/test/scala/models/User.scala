package models

import sqlkit._
import sqlkit.binder.crypto
import sqlkit.binder.crypto.Crypto
import sqlkit.session.SqlSession
import sqlkit.utils.Timing

import java.util.Base64

case class User(
  id: Long,
  email: String,
  emailCrypted: String,
  intCrypted: Int
) extends SqlModelAutoInc[User] {
  def withId(id: Long) = this.copy(id = id)
}

object User extends SqlTableAutoInc[User] {

  def table(alias: String) = TableDef(alias)

  case class TableDef(alias: String) extends SqlTableDef[User] {

    val table = "user"

    val base64Crypt = new Crypto {

      override def encrypt(s: String): String = {
        new String(Base64.getEncoder.encode(s.getBytes))
      }

      override def decrypt(s: String): String =
        new String(Base64.getDecoder.decode(s))
    }

    val id = column[Long]("id", primaryKey = true)
    val email = column[String]("email")
    val emailCrypted = column[String]("email_crypted")(crypto.CryptedToSql[String](base64Crypt))
    val intCrypted = columnCrypted[Int]("int_crypted")(base64Crypt)

    def fromSql(row: SqlRow) = User(
      id = row.get(id), email = row.get(email), emailCrypted = row.get(emailCrypted), intCrypted = row.get(intCrypted)
    )

    def toSql(user: User) = List(
      id -> user.id,
      email -> user.email,
      emailCrypted -> user.emailCrypted,
      intCrypted -> user.intCrypted
    )
  }

  //override def autoSession = SqlAuto("pool")

  private val byNameSql = withSql(table) { user =>
    sql"""
      SELECT ${user.*}
      FROM $user
      WHERE ${user.email} = :name
    """.as(user)
  }

  def byName(name: String)(implicit session: SqlSession = autoSession) = Timing.time("byName") {
    byNameSql.on(
      "name" -> name
    ).first
  }

  def byName2(name: String)(implicit session: SqlSession = autoSession) = Timing.time("byName2") {
    where { user =>
      sql"""${user.email} = $name"""
    }.first
  }

  def byNameOpt(name: Option[String])(implicit session: SqlSession = autoSession) = Timing.time("byNameOpt") {
    where { user =>
      sql"""${name.map(n => sql"${user.email} = $n")}"""
    }.first
  }

  def byRoleSimple(name: String)(implicit session: SqlSession = autoSession) = {

    withSql(User.table, Role.table, UserRole.table) { case (user, role, userRole) =>
      sql"""
          SELECT ${user.*}, ${role.*}
          FROM ${user}
          LEFT JOIN ${userRole} ON ${userRole.userId} = ${user.id}
          LEFT JOIN ${role} ON ${role.id} = ${userRole.roleId}
          WHERE ${role.name} = :name
        """.as { row => (user.fromSql(row), role.fromSql(row)) }
    }.on(
      "name" -> name
    ).list
    //.map { case (user, roles) => user.copy(roles = roles)}
  }

  def oneTheFly(email: String)(implicit session: SqlSession = autoSession) = {

    withSql(User.table) { case (user) =>
      sql"""
          SELECT ${user.*}
          FROM ${user}
          WHERE ${user.email} = $email
        """.as { row => user.fromSql(row) }
    }.list
  }

  def byRole(name: String)(implicit session: SqlSession = autoSession) = {

    Timing.time("query")(withSql(User.table, Role.table, UserRole.table) { case (user, role, userRole) =>
      sql"""
          SELECT ${user.*}, ${role.*}
          FROM ${user}
          LEFT JOIN ${userRole} ON ${userRole.userId} = ${user.id}
          LEFT JOIN ${role} ON ${role.id} = ${userRole.roleId}
          WHERE ${role.name} = :name
        """.oneToMany(
        row => user.fromSql(row),
        row => role.fromSql(row),
      )
    }).on(
      "name" -> name
    ).list
    //.map { case (user, roles) => user.copy(roles = roles)}
  }

  def byRoleCompiled(name: String)(implicit session: SqlSession = autoSession) = {

    Timing.time("query compiled")(withSqlCompiled(User.table, Role.table, UserRole.table) { case (user, role, userRole) =>

      sql"""
          SELECT ${user.*}, ${role.*}
          FROM ${user}
          LEFT JOIN ${userRole} ON ${userRole.userId} = ${user.id}
          LEFT JOIN ${role} ON ${role.id} = ${userRole.roleId}
          WHERE ${role.name} = :name
        """.oneToMany(
        row => user.fromSql(row),
        row => role.fromSql(row),
      )

    }).on(
      "name" -> name
    ).list
    //.map { case (user, roles) => user.copy(roles = roles)}
  }

  def testPagination(name: String, offset: Long, limit: Long)(implicit session: SqlSession = autoSession) = {
    where { user =>
      sql"""${user.email} = $name"""
    }.list(offset, limit)
  }

}
