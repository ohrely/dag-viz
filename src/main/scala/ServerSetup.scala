/**
  * Created by rely10 on 4/25/16.
  */
import org.http4s._
import org.http4s.dsl._
import org.http4s.server.blaze.BlazeBuilder


object SimpleService {
  val service = HttpService {
    case GET -> Root =>
      Ok("Hello world.")
  }
}

object SimpleServer {
  def runOnPort(port: Int) = {
    BlazeBuilder
      .bindHttp(port)
      .mountService(SimpleService.service, "/")
      .run
  }

  def main(args: Array[String]) = {
    val res = runOnPort(8080)
    res.awaitShutdown()
  }
}