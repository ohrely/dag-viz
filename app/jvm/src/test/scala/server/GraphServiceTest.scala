package server

import org.http4s.StaticFile
import org.http4s.dsl._
import org.scalatest.FunSuite

import scalaz.concurrent.Task

/**
  * Created by rely10 on 5/10/16.
  */
class GraphServiceTest extends FunSuite {
  test("trying"){
    println("yes")

    val path = "/"

    println(System.getProperty("user.dir"))
//    val req:

//    StaticFile.fromString(path, Some(req))
//      .map(Task.now)
//      .getOrElse(Ok("Nooooooo."))
  }
}
