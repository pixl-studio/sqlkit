package sqlkit.binder

import sqlkit.binder.numeric._
import sqlkit.binder.time.{DurationToSql, InstantToSql, LocalDateTimeToSql, LocalDateToSql, LocalTimeToSql, OffsetDateTimeToSql, ZonedDateTimeToSql}

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
      override def toSql(mv: Option[T], statement: PreparedStatement, index: Int): Unit = {
        mv.map { v =>
          self.toSql(v, statement, index)
        }.getOrElse {
          statement.setNull(index, java.sql.Types.TIMESTAMP)
        }
      }
    }
  }
}

object SqlBinder {

  implicit def optSql[T](implicit binder: SqlBinder[T]): SqlBinder[Option[T]] = binder.toOption

  implicit val uuidToSql: UUIDToSql = new UUIDToSql()
  implicit val stringToSql: StringToSql = new StringToSql()

  implicit val booleanToSql: BooleanToSql = new BooleanToSql()

  implicit val shortToSql: ShortToSql = new ShortToSql()
  implicit val intToSql: IntToSql = new IntToSql()
  implicit val longToSql: LongToSql = new LongToSql()
  implicit val floatToSql: FloatToSql = new FloatToSql()
  implicit val doubleToSql: DoubleToSql = new DoubleToSql()
  implicit val bigDecimalToSql: BigDecimalToSql = new BigDecimalToSql()

  implicit val localDateToSql: LocalDateToSql = new LocalDateToSql()
  implicit val localDateTimeToSql: LocalDateTimeToSql = new LocalDateTimeToSql()
  implicit val localTimeToSql: LocalTimeToSql = new LocalTimeToSql()
  implicit val instantToSql: InstantToSql = new InstantToSql()
  implicit val durationToSql: DurationToSql = new DurationToSql()
  implicit val zonedDateTimeToSql: ZonedDateTimeToSql = new ZonedDateTimeToSql()
  implicit val offsetDateTimeToSql: OffsetDateTimeToSql = new OffsetDateTimeToSql()

}