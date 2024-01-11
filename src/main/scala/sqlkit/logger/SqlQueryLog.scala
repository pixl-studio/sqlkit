package sqlkit.logger

import java.time.LocalDateTime

case class SqlQueryLog(
  session:String,
  uuid:String,
  sql:String,
  time:Option[Long],
  rows:Option[Long],
  date:LocalDateTime=LocalDateTime.now
)
