package graph

import org.scalatest._

/**
  * Created by rely10 on 3/30/16.
  */

class GraphTest extends FunSuite{
  val n1 = new Node("123", "node 1")
  val n2 = new Node("234", "node 2")
  println(n1)

  val e1 = new Edge("abc", n1, n2)
  println(e1)
}
