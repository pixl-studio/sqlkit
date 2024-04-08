package sqlkit

case class SqlList[T](
  data: List[T],
  count: Long,
  offset: Option[Long],
  limit: Option[Long],
){
  def map[X](f:T => X): SqlList[X] = {
    this.copy(
      data = data.map(f)
    )
  }
}