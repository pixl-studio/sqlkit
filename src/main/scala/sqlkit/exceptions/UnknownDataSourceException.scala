package sqlkit.exceptions

case class UnknownDataSourceException(
  name:String
) extends Exception(s"unknown dataSource: ${name}")
