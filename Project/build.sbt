name := """Project_Poseidon"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs
)

libraryDependencies ++= Seq("commons-io" % "commons-io" % "2.4", "org.bouncycastle" % "bcprov-jdk15on" % "1.51")