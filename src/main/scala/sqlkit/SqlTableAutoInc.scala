package sqlkit

trait SqlTableAutoInc[T <: SqlModelAutoInc[T]] extends SqlTableAutoTyped[T, Long]

