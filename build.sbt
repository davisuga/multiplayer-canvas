val scala3Version = "3.2.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "multiplayer-canvas",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test
  )
libraryDependencies += "com.github.ghostdogpr" %% "caliban" % "2.0.1"
libraryDependencies += "com.github.ghostdogpr" %% "caliban-http4s" % "2.0.1" // routes for http4s
libraryDependencies += "com.github.ghostdogpr" %% "caliban-zio-http" % "2.0.1" // routes for zio-http
