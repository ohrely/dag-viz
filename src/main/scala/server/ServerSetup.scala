package server

/**
  * Created by rely10 on 4/25/16.
  */
import graph.GraphUtil._
import org.http4s._
import org.http4s.dsl._
import org.http4s.server.blaze.BlazeBuilder
import org.scalajs.dom

import scala.scalajs.js.annotation.JSExport


@JSExport
object SimpleService {
  @JSExport
  val service = HttpService {
    case GET -> Root =>
      viz.VizUtil.main(dom.document.getElementById("mod"), graph.TestGraph.graph)
  }
}

object SimpleServer {
  def runOnPort(port: Int) = {
    BlazeBuilder
      .bindHttp(port)
      .mountService(SimpleService.service, "/")
      .run
  }

  def runVizOnPort(graph: Graph, port: Int) = {

  }

  def main(args: Array[String]) = {
    val res = runOnPort(8080)
    res.awaitShutdown()
  }
}