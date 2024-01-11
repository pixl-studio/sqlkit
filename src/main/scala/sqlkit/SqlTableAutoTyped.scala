package sqlkit

import sqlkit.session.SqlSession

trait SqlTableAutoTyped[T <: SqlModelAutoTyped[T, K], K] extends SqlTable[T] {

  override def insert(entity: T)(implicit session: SqlSession = autoSession): T = {
    val result = insertSql(entity).exec[K]
    result.generatedKeys.headOption.map { id =>
      entity.withId(id)
    } getOrElse entity
  }

  override def batchInsert(entities: List[T], batchSize:Int=100)(implicit session: SqlSession = autoSession): List[T] = {
    entities.headOption.map { entity =>
      val result = insertSql(entity).execBatch[K,T](entities.map(insertSqlParam), batchSize)
      entities.zip(result.generatedKeys).map { case (entity, key) =>
        entity.withId(key)
      }
    }.getOrElse(Nil)
  }
}
