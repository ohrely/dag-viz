package graph

import GraphUtil._

import org.scalatest._

/**
  * Created by rely10 on 3/30/16.
  */

class GraphUtilTest extends FunSuite {
  //def main(args: Array[String]) {
  val nodes = (1 to 3).toList.map(i => Node(i, NodeProps(i.toString)))

  println(nodes)

  val edges = List(Edge(1, 1, 2, EdgeProps("a")),
    Edge(2, 2, 3, EdgeProps("b")))

  println(edges)

//  val graph = Graph(nodes, edges)
//
//  println(graph)

}