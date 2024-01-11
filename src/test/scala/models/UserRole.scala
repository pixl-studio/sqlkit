package models

import sqlkit.{SqlModel, SqlRow, SqlTable, SqlTableDef}

case class UserRole(
  id: Long,
  userId: Long,
  roleId: Long
) extends SqlModel[UserRole]

object UserRole extends SqlTable[UserRole] {

  def table(alias: String) = TableDef(alias)
  case class TableDef(alias:String) extends SqlTableDef[UserRole]{

    val table = "user_role"

    val id = column[Long]("id", primaryKey = true)
    val userId = column[Long]("user_id")
    val roleId = column[Long]("role_id")

    def fromSql(row: SqlRow) = UserRole(
      id = row.get(id), userId = row.get(userId), roleId = row.get(roleId)
    )

    def toSql(o: UserRole) = List(
      id -> o.id, userId -> o.userId, roleId -> o.roleId
    )
  }

}
