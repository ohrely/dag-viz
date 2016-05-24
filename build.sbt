name := "DAGvizSF"

version := "1.0"

scalaVersion := "2.11.7"

val http4sVersion = "0.13.2"

val app = crossProject.settings(
  unmanagedSourceDirectories in Compile +=
    baseDirectory.value  / "shared" / "main" / "scala",
  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "2.2.1" % "test" withSources(),
    "ch.qos.logback" % "logback-classic" % "1.0.13",

    "org.apache.spark" %% "spark-core" % "1.6.1",
    "org.apache.spark" %% "spark-sql" % "1.6.1",

    "org.http4s" %% "http4s-dsl" % http4sVersion,
    "org.http4s" %% "http4s-server" % http4sVersion,
    "org.http4s" %% "http4s-blaze-server" % http4sVersion,
    "org.http4s" %% "http4s-blaze-client" % http4sVersion
  ),
  scalaVersion := "2.11.5"
).jsSettings(
  libraryDependencies ++= Seq(
    "com.lihaoyi" %%% "scalatags" % "0.4.6",
    "org.scala-js" %%% "scalajs-dom" % "0.8.0"
  )
).jvmSettings(
  libraryDependencies ++= Seq(
  )
).enablePlugins(ScalaJSPlugin)

lazy val appJS = app.js
lazy val appJVM = app.jvm.settings(
  (resources in Compile) ++= Seq(
    (fastOptJS in (appJS, Compile)).value.data,
    (fullOptJS in (appJS, Compile)).value.data
  )
)

persistLauncher := true
//persistLauncher in Test := false
//persistLauncher in Compile := true

// May ultimately be the better way to go:
// val scalajsOutputDir = Def.settingKey[File](resourceDirectory.toString)

