package models

import sqlkit.{SqlModelAutoInc, SqlRow, SqlTable, SqlTableDef}

case class Role(
  id: Long,
  name: String
) extends SqlModelAutoInc[Role] {
  def withId(id: Long) = this.copy(id = id)
}

object Role extends SqlTable[Role] {

  def table(alias: String) = TableDef(alias)
  case class TableDef(alias: String) extends SqlTableDef[Role] {

    val table = "role"

    val id = column[Long]("id", primaryKey = true)
    val name = column[String]("name")

    def fromSql(row: SqlRow) = Role(
      id = row.get(id), name = row.get(name)
    )

    def toSql(role: Role) = List(
      id -> role.id, name -> role.name
    )
  }

}
