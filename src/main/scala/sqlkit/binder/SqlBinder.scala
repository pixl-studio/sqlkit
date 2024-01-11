package sqlkit.binder

import sqlkit.binder.numeric._
import sqlkit.binder.time.{DurationToSql, InstantToSql, LocalDateTimeToSql, LocalDateToSql, LocalTimeToSql}

import java.sql.{PreparedStatement, ResultSet}

trait SqlBinder[T] {
  self =>

  def apply(rs: ResultSet, columnIndex: Int): T = fromSql(rs.getObject(columnIndex))
  def apply(rs: ResultSet, columnLabel: String): T = fromSql(rs.getObject(columnLabel))

  def wrap(o:T):String = o.toString
  def unwrap(o:String):T

  def fromSql(o: Object): T
  def toSql(v: T, statement: PreparedStatement, index: Int): Unit

  def map[U](f: T => U, g: U => T): SqlBinder[U] = new SqlBinder[U] {

    def unwrap(o:String): U = f(self.unwrap(o))

    def fromSql(o: Object): U = f(self.fromSql(o))
    def toSql(v: U, statement: PreparedStatement, index: Int): Unit = self.toSql(g(v), statement, index)
  }

  def toOption: SqlBinder[Option[T]] = {
    new SqlBinder[Option[T]] {

      def unwrap(o:String): Option[T] = Option(self.unwrap(o))

      override def fromSql(o: Object): Option[T] = {
        if (o == null) None else Some(self.fromSql(o))
      }
      override def toSql(v: Option[T], statement: PreparedStatement, index: Int): Unit = {
        self.toSql(v.getOrElse(null.asInstanceOf[T]), statement, index)
      }
    }
  }
}

object SqlBinder {

  implicit def optSql[T](implicit binder: SqlBinder[T]): SqlBinder[Option[T]] = binder.toOption

  implicit val uuidToSql = new UUIDToSql()
  implicit val stringToSql = new StringToSql()

  implicit val shortToSql = new ShortToSql()
  implicit val intToSql = new IntToSql()
  implicit val longToSql = new LongToSql()
  implicit val floatToSql = new FloatToSql()
  implicit val doubleToSql = new DoubleToSql()
  implicit val bigDecimalToSql = new BigDecimalToSql()

  implicit val localDateToSql = new LocalDateToSql()
  implicit val localDateTimeToSql = new LocalDateTimeToSql()
  implicit val localTimeToSql = new LocalTimeToSql()
  implicit val instantToSql = new InstantToSql()
  implicit val durationToSql = new DurationToSql()

}