package sqlkit.utils

import java.util.concurrent.TimeUnit
import scala.concurrent.{ExecutionContext, Future}

object Timing {

  def traceOpt[R](block: => R, doTrace:Boolean): (R, Option[Long]) = {
    if(!doTrace) (block, None) else {
      val (result, duration) = trace(block)
      (result, Some(duration))
    }
  }

  def trace[R](block: => R): (R, Long) = {
    val t0 = System.nanoTime()
    val result = block
    val t1 = System.nanoTime()
    (result, t1-t0)
  }

  def time[R](name:String, unit:TimeUnit=TimeUnit.MILLISECONDS)(block: => R): R = {
    val t0 = System.nanoTime()
    val result = block
    val t1 = System.nanoTime()

    timePrint(name, unit, t1-t0)

    result
  }

  def timeF[R](name:String, unit:TimeUnit=TimeUnit.MILLISECONDS)(block: => Future[R])(implicit ec:ExecutionContext): Future[R] = {
    val t0 = System.nanoTime()
    block.map { result =>
      val t1 = System.nanoTime()

      timePrint(name, unit, t1-t0)

      result
    }
  }

  def timePrint(name:String, unit:TimeUnit=TimeUnit.MILLISECONDS, duration:Long, durationUnit:TimeUnit=TimeUnit.NANOSECONDS) = {
    println(timeFormat(name, unit, duration, durationUnit))
  }
  def timeFormat(name:String, unit:TimeUnit=TimeUnit.MILLISECONDS, duration:Long, durationUnit:TimeUnit=TimeUnit.NANOSECONDS) = {

    val (abvr, subunit, subscale) = unit match {
      case TimeUnit.DAYS =>
        ("d", TimeUnit.HOURS, 2)

      case TimeUnit.HOURS =>
        ("h", TimeUnit.MINUTES, 2)

      case TimeUnit.MINUTES =>
        ("mn", TimeUnit.SECONDS, 2)

      case TimeUnit.SECONDS =>
        ("s", TimeUnit.MILLISECONDS, 3)

      case TimeUnit.MILLISECONDS =>
        ("ms", TimeUnit.MICROSECONDS, 3)

      case TimeUnit.MICROSECONDS =>
        ("μs", TimeUnit.NANOSECONDS, 3)

      case _ =>
        ("ns", TimeUnit.NANOSECONDS, 0)
    }

    val time = unit.convert(duration, durationUnit)

    val subtime = subunit.convert(duration - unit.toNanos(time), TimeUnit.NANOSECONDS)
    val subtimeStr = if (subtime>0) s".${padLeft(subtime.toString, subscale, '0')}" else ""
    s"${name}: ${time}${subtimeStr}$abvr"
  }

  def padLeft(s:String, nb: Int, char: Char): String = {
    s.reverse.padTo(nb, char).reverse.mkString
  }
}
