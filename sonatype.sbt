import xerial.sbt.Sonatype.GitHubHosting

credentials += Credentials(Path.userHome / ".sbt" / "credentials")

sonatypeProfileName := "sqlkit"

// To sync with Maven central, you need to supply the following information:
publishMavenStyle := true

// License of your choice
licenses := Seq("APL2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))

// Where is the source code hosted
sonatypeProjectHosting := Some(GitHubHosting("io.github.sqlkit", "sqlkit", "benoit.ponsero@pixlstudio.fr"))

// or if you want to set these fields manually
homepage := Some(url("https://sqlkit.github.io/sqlkit/"))
scmInfo := Some(
  ScmInfo(
    url("https://github.com/sqlkit/sqlkit.git"),
    "scm:git@github.com:sqlkit/sqlkit.git"
  )
)
developers := List(
  Developer(id="bpo", name="Benoit ponsero", email="benoit.ponsero@pixlstudio.fr", url=url("https://github.com/sqlkit/sqlkit.git"))
)
