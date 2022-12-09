val scala3Version = "3.2.1"
val calibanVersion = "2.0.1"
val http4sVersion = "0.23.16"

lazy val root = project
  .in(file("."))
  .settings(
    name := "multiplayer-canvas",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    crossPaths := false,
    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test
  )

val cassandraLibs =
  Seq(
    ("com.datastax.oss" % "java-driver-core" % "4.15.0").from(
      "https://repo1.maven.org/maven2/com/datastax/oss/java-driver-core/4.15.0/java-driver-core-4.15.0.jar"
    ),
    ("com.datastax.oss" % "java-driver-query-builder" % "4.15.0").from(
      "https://repo1.maven.org/maven2/com/datastax/oss/java-driver-query-builder/4.15.0/java-driver-query-builder-4.15.0.jar"
    ),
    ("com.datastax.oss" % "java-driver-mapper-processor" % "4.15.0").from(
      "https://repo1.maven.org/maven2/com/datastax/oss/java-driver-mapper-processor/4.15.0/java-dri" +
        "ver-mapper-processor-4.15.0.jar"
    )
  )

val redisLibs = Seq(
  "dev.profunktor" %% "redis4cats-effects" % "1.3.0"
)

libraryDependencies ++= redisLibs

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
