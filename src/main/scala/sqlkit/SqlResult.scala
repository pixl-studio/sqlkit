package sqlkit

case class SqlResult[K](
  affectedRows:Long, generatedKeys:List[K]
)