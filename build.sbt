enablePlugins(ScalaJSPlugin)

import com.lihaoyi.workbench.Plugin._

workbenchSettings

name := "DAGvizSF"

version := "1.0"

scalaVersion := "2.11.7"

val http4sVersion = "0.13.2"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.1" % "test" withSources(),
  "org.scala-js" %%% "scalajs-dom" % "0.8.2",
// TODO: approval
  "com.lihaoyi" %%% "scalatags" % "0.5.4",
  "org.apache.spark" %% "spark-core" % "1.6.1",
  "org.apache.spark" %% "spark-sql" % "1.6.1",

// TODO: approval
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-server" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "ch.qos.logback" % "logback-classic" % "1.0.13"
)

//persistLauncher := true
//persistLauncher in Test := false

bootSnippet := "viz.VizUtil().main();"

//mainClass in (Compile, run) := Some("viz.VizUtil().main()")

// configure a specific directory for scalajs output - TODO: make work
//val scalajsOutputDir = Def.settingKey[File]("main/resources")

updateBrowsers <<= updateBrowsers.triggeredBy(fastOptJS in Compile)

persistLauncher in Compile := true