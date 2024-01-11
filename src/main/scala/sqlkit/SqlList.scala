package sqlkit

case class SqlList[T](
  data: List[T],
  offset: Long,
  limit: Long,
  total: Long
)