package graphpack

import graphpack.GraphUtil._

/**
  * Created by rely10 on 4/26/16.
  */
object TestGraph {
  val nodes = (0 to 7).toList.map(i => Node(i, NodeProps(i.toString, (0 to i).toArray.map(i => i.toString), (0 to 3).toArray.map(x => (0 to i).toArray.map(i => (2 - i).toString)))))
  val edges = List(
    Edge(11, 0, 3, EdgeProps("yuss")),
    Edge(12, 1, 4, EdgeProps("yes")),
    Edge(13, 2, 6, EdgeProps("yes")),
    Edge(21, 3, 5, EdgeProps("yes")),
    Edge(22, 4, 6, EdgeProps("yes")),
    Edge(31, 5, 7, EdgeProps("yes")),
    Edge(32, 6, 7, EdgeProps("yes"))
  )

  val test_graph: Graph = Graph(nodes, edges)
}
