package server

/**
  * Created by rely10 on 4/25/16.
  */
import graphpack.GraphUtil._
import graphpack.TestGraph.test_graph
import org.http4s._
import org.http4s.dsl._
import org.http4s.server.blaze.BlazeBuilder

import scalaz.concurrent.Task

//import scala.concurrent.{ExecutionContext, Future}
//import scala.concurrent.ExecutionContext.Implicits.global
//import org.http4s.headers.`Content-Type`
//import org.http4s.MediaType._
//import scalaz.stream.time.awakeEvery
//import scala.concurrent.duration._


class GraphService(graph: Graph){
  def fetchStatic(path: String, req: Request) = {
    StaticFile.fromString(path, Some(req))
      .map(Task.now)
      .getOrElse(Ok("Nooooooo."))
  }

  val nongraph: String = "ceci n'est pas un graphique"

  def service = HttpService {
    case req @ GET -> Root =>
      fetchStatic("src/main/resources/index-dev.html", req)

    case req @ GET -> Root / "dag" =>
      Ok(nongraph)

    case req @ GET -> path =>
      fetchStatic("target/scala-2.11" + path.toString, req)
  }
}

object SimpleServer extends App {
  def runVizOnPort(graph: Graph = test_graph, port: Int) = {
    val s = new GraphService(graph)

    BlazeBuilder
      .bindHttp(port)
      .mountService(s.service, "/")
      .run
  }

  def main(graph: Graph = test_graph, port: Int = 8083) = {
    val res = runVizOnPort(graph, port)
    res.awaitShutdown()
  }

  main()
}