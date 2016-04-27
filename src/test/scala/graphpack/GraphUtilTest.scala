package graphpack

import graphpack.GraphUtil._
import org.scalatest._

/**
  * Created by rely10 on 3/30/16.
  */

class GraphUtilTest extends FunSuite {
  println()

  val nodes = (0 to 7).toList.map(i => Node(i, NodeProps(i.toString, (0 to i).toArray.map(i => i.toString), (0 to 3).toArray.map(x => (0 to i).toArray.map(i => (60 - i).toString)))))
  val edges = List(
    Edge(11, 0, 3, EdgeProps("yes")),
    Edge(12, 1, 4, EdgeProps("yes")),
    Edge(13, 2, 4, EdgeProps("yes")),
    Edge(21, 3, 5, EdgeProps("yes")),
    Edge(22, 4, 6, EdgeProps("yes")),
    Edge(31, 5, 7, EdgeProps("yes")),
    Edge(32, 6, 7, EdgeProps("yes"))
  )

  println("Nodes: " + nodes)
  println("Edges: " + edges)

  val graph = new Graph(nodes, edges)

  println()
  println("Graph: " + graph)
  println()

}