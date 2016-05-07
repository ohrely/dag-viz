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
import org.http4s.headers.`Content-Type`
import org.http4s.MediaType._
import scalaz.concurrent.Task
import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.ExecutionContext.Implicits.global

//import scalaz.stream.time.awakeEvery
//import scala.concurrent.duration._


class GraphService(graph: Graph){
  def fetchResource(path: String, req: Request) = {
    StaticFile.fromResource(path, Some(req))
      .map(Task.now)
      .getOrElse(Ok("Nooooooo."))
//      .getOrElse(NotFound())
  }

  def service = HttpService {
    case req @ GET -> Root =>
      fetchResource("/index-dev.html", req)

    //  goes to "resources" folder to retrieve requested static file
    case req @ GET -> path =>
      fetchResource(path.toString, req)

//    case req @ GET -> Root / "slow-body" =>
//      val resp = "Hello world!".map(_.toString())
//      val body = awakeEvery(2.seconds).zipWith(Process.emitAll(resp))((_, c) => c)
//      Ok(body)
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