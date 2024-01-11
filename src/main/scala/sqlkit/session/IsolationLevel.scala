package sqlkit.session

sealed trait IsolationLevel

object IsolationLevel {

  case object Serializable extends IsolationLevel
  case object RepeatableRead extends IsolationLevel
  case object ReadCommitted extends IsolationLevel
  case object ReadUncommitted extends IsolationLevel
  case object Default extends IsolationLevel

}