package server

/**
  * Created by rely10 on 4/25/16.
  */
import graphpack.GraphUtil._
import graphpack.TestGraph.test_graph
import viz.VizUtil
import org.http4s._
import org.http4s.dsl._
import org.http4s.server.blaze.BlazeBuilder
import scalaz.concurrent.Task

//import scalaz.stream.time.awakeEvery
//import scala.concurrent.duration._


class SimpleService(graph: Graph){
  def fetchResource(path: String, req: Request): Task[Response] = {
    StaticFile.fromResource(path, Some(req))
      .map(Task.now)
      .getOrElse(Ok("Nooooooo."))
//      .getOrElse(NotFound())
  }

  val service = HttpService {
    case req @ GET -> Root =>
      val resourcePath = "/index-dev.html"
////      turn graph into viz
//      VizUtil.main(graph)
////      pass viz to page
      fetchResource(resourcePath, req)

//    case req @ GET -> Root / "slow-body" =>
//      val resp = "Hello world!".map(_.toString())
//      val body = awakeEvery(2.seconds).zipWith(Process.emitAll(resp))((_, c) => c)
//      Ok(body)
  }
}

object SimpleServer extends App {
  def runVizOnPort(graph: Graph = test_graph, port: Int) = {
    val s = new SimpleService(graph)

    BlazeBuilder
      .bindHttp(port)
      .mountService(s.service, "/")
      .run
  }

  def main(graph: Graph = test_graph, port: Int = 8082) = {
    val res = runVizOnPort(graph, port)
    res.awaitShutdown()
  }

  main()
}