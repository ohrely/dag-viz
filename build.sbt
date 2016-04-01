//import com.lihaoyi.workbench.Plugin._

enablePlugins(ScalaJSPlugin)

name := "DAGvizSF"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.1" % "test" withSources(),
  "org.scala-js" %%% "scalajs-dom" % "0.8.2",
  "com.lihaoyi" %%% "scalatags" % "0.5.4"
)

//bootSnippet := "example.ScalaJSExample().main(document.getElementById('canvas'));"

//updateBrowsers <<= updateBrowsers.triggeredBy(fastOptJS in Compile)