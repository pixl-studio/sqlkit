

val scala3Version = "3.1.0"

lazy val sqlkit = project
  .in(file("."))
  .settings(
    name := "sqlkit",
    version := "0.1",

    scalaVersion := "2.13.14",

    libraryDependencies ++= Seq(
      "com.zaxxer" % "HikariCP" % "5.1.0",

      "mysql" % "mysql-connector-java" % "5.1.44" % "test",
      "org.scalatest" %% "scalatest" % "3.2.11" % "test",
      "org.slf4j" % "slf4j-nop" % "1.7.31" % Test
    )
  )
