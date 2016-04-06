package graph

import GraphUtil._

import org.scalatest._

/**
  * Created by rely10 on 3/30/16.
  */

class GraphUtilTest extends FunSuite {
  println()

  val nodes = (0 to 4).toList.map(i => Node(i, NodeProps(i.toString)))
  val edges = List(
    Edge(1, 1, 2, EdgeProps("yes")),
    Edge(2, 2, 3, EdgeProps("yes")),
    Edge(3, 1, 3, EdgeProps("yes")),
    Edge(4, 4, 1, EdgeProps("yes"))
  )

  println("Nodes: " + nodes)
  println("Edges: " + edges)

  val graph = Graph(nodes, edges)

  println()
  println("Graph: " + graph)
  println("Rows: " + graph.rows)
  println()

}