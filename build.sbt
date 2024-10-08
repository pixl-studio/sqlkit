

val scala3 = "3.1.0"
val Scala212 = "2.12.20"
val Scala213 = "2.13.14"

lazy val sqlkit = project
  .in(file("."))
  .settings(
    name := "sqlkit",
    version := "0.1",

    scalaVersion := Scala213,
    crossScalaVersions := Seq(Scala212, Scala213),

    libraryDependencies ++= Seq(
      "com.zaxxer" % "HikariCP" % "5.1.0",

      "mysql" % "mysql-connector-java" % "5.1.44" % "test",
      "org.scalatest" %% "scalatest" % "3.2.11" % "test",
      "org.slf4j" % "slf4j-nop" % "1.7.31" % Test
    )
  )
