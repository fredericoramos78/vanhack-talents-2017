import com.typesafe.config._


val conf = ConfigFactory.parseFile(new File("conf/application.conf")).resolve()


name := """vanhack-forum-logic"""
organization in ThisBuild := "com.framos"

version := conf.getString("app.version")


lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.4"

// Scala Compiler Options
scalacOptions in ThisBuild ++= Seq(
  "-target:jvm-1.8",
  "-encoding", "UTF-8",
  "-deprecation", // warning and location for usages of deprecated APIs
  "-feature", // warning and location for usages of features that should be imported explicitly
  "-unchecked", // additional warnings where generated code depends on assumptions
  "-Xlint", // recommended additional warnings
  "-Ywarn-adapted-args", // Warn if an argument list is modified to match the receiver
  "-Ywarn-value-discard", // Warn when non-Unit expression results are unused
  "-Ywarn-inaccessible",
  "-Ywarn-dead-code"
)

lazy val akkaVersion = "2.5.8"

libraryDependencies ++= Seq(
    ehcache,
    ws,
    filters,
    jdbc,
    guice,
    evolutions,
    "org.webjars" %% "webjars-play" % "2.6.+",
    "net.codingwell" %% "scala-guice" % "4.+",
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "com.typesafe.play" %% "anorm" % "2.5+",
    "mysql" % "mysql-connector-java" % "6.+",
    // testcase libs
    "org.scalactic" %% "scalactic" % "3.0.+",
    "org.mockito" % "mockito-core" % "2.+" % "test",
    "org.scalatest" %% "scalatest" % "3.0.+" % "test",
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test"
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator


// Do not generate Play/Scala docs
sources in (Compile, doc) := Seq.empty
publishArtifact in (Compile, packageDoc) := false
