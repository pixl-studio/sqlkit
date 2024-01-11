package sqlkit

import sqlkit.session.{SqlAuto, SqlSession}

trait SqlModel[T <: SqlModel[T]] {

  def insert()(implicit session: SqlSession = SqlAuto(), table: SqlTable[T]): T = {
    table.insert(this.asInstanceOf[T])
  }

  def update()(implicit session: SqlSession = SqlAuto(), table: SqlTable[T]): T = {
    table.update(this.asInstanceOf[T])
  }

  def delete()(implicit session: SqlSession = SqlAuto(), table: SqlTable[T]): Long = {
    table.delete(this.asInstanceOf[T])
  }
}

trait SqlModelAutoTyped[T <: SqlModel[T], K] extends SqlModel[T] {
  val id: K
  def withId(id: K): T
}

trait SqlModelAutoInc[T <: SqlModel[T]] extends SqlModelAutoTyped[T, Long] {
  val id: Long
  def withId(id: Long): T
}

