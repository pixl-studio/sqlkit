
import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import models.User
import org.scalatest.funsuite.AnyFunSuite
import sqlkit.session.SqlAuto
import sqlkit.{DB, sqlInterpolation}
import sqlkit.utils.Timing

class ModelTest extends AnyFunSuite {

  def init() = {
    val poolConfig = new HikariConfig
    poolConfig.setDriverClassName("com.mysql.jdbc.Driver")
    poolConfig.setJdbcUrl("jdbc:mysql://localhost:3306/sqlkit?useSSL=false")
    poolConfig.setUsername("sqlkit")
    poolConfig.setPassword("sqlkit")
    poolConfig.addDataSourceProperty("cachePrepStmts", "true")
    poolConfig.addDataSourceProperty("prepStmtCacheSize", "250")
    poolConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
    poolConfig.setMaximumPoolSize(5)

    DB.add(new HikariDataSource(poolConfig))

  }

  def loop(f: => Unit, n: Int = 100) = {
    (0 to n).foreach(i => f)
  }
  // ---

  test("general") {

    init()

    //loop(User.byName("byName 1"))
    //loop(User.byName2("byName 2"))
    //User.byNameOpt(None)
    //val user = User.byName2("benoit.ponsero@pixlstudio.fr")
    //val user = User.byNameOpt(Some("benoit.ponsero@pixlstudio.fr"))
    //println(user)



    //println("query")
    //loop(User.byRole("test"))
    //println("compiled")
    //loop(User.byRoleCompiled("test"))

    implicit val dbSession = SqlAuto()

    //User.byId(1L)
    //User.create(User(0L, 2L, "qq"))
    //User.update(User(0L, 2L, "kk"))
    //User.delete(User(0L, 2L, "kk"))
    val qq = User.qq("user_0")

    println("----")
    println(qq)

    val qq2 = User.qq2("user_0")

    //User.byName2("byName2 1")

    //val r = Role(name= "sqlkit/test")

    //r.insert
    //r.update
    //r.delete

    //Role.create(r)
    //Role.update(r)
    //Role.byId(UUID.randomUUID)

    /*
    val queries = DB.logger.get(dbSession.uuid)
    queries.map { query =>

      println(
        s"""
           |Query log:
           |date: ${query.date}
           |time: ${query.time.getOrElse("-")}
           |rows: ${query.rows.getOrElse("-")}
           |query: ${query.sql}""".stripMargin)
    }

     */

    DB.close()
  }

  test("tx"){
    init()

    DB.withTransaction(){ implicit tx =>

      val users = (0 to 100).map { i =>
        User(id=0, email = s"user_${i}", "", 1)
      }.toList

      val updatedUsers = User.batchInsert(users, 10)

      //updatedUsers.foreach( u => println(u.id))
    }

    DB.close()
  }
}