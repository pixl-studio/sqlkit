package sqlkit

case class SqlList[T](
  rows: List[T],
  count: Long,
  offset: Option[Long] = None,
  limit: Option[Long] = None,
){
  def map[X](f:T => X): SqlList[X] = {
    this.copy(
      rows = rows.map(f)
    )
  }
}