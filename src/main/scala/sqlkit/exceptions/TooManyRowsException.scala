package sqlkit.exceptions

case class TooManyRowsException(msg:String) extends Exception(msg)
