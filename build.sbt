val scala3Version = "3.2.1"
val calibanVersion = "2.0.1"
val http4sVersion = "0.23.16"

lazy val root = project
  .in(file("."))
  .settings(
    name := "multiplayer-canvas",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test
  )

libraryDependencies ++= Seq(
  "com.github.ghostdogpr" %% "caliban" % calibanVersion,
  "com.github.ghostdogpr" %% "caliban-http4s" % calibanVersion,
  "com.github.ghostdogpr" %% "caliban-cats" % calibanVersion,
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % "0.23.11",
  "co.fs2" %% "fs2-core" % "3.4.0"
)
scalacOptions += "-Xmax-inlines:64"
val circeVersion = "0.14.1"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)
