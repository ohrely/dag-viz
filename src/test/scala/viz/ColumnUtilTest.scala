package viz

import graph.GraphUtil._
import ColumnUtil._
import org.scalatest._

/**
  * Created by rely10 on 4/1/16.
  */
class ColumnUtilTest extends FunSuite{
  val nodes = Set(0,1,2,3,4)

  val edges = Array(
    Edge(1, 1, 2, EdgeProps("yes")),
    Edge(2, 2, 3, EdgeProps("yes")),
    Edge(3, 1, 3, EdgeProps("yes")),
    Edge(4, 4, 1, EdgeProps("yes"))
  )

  val nodeCols: Map[Int, Set[Int]] = Map()
  val col: Int = 0

  var mapped: Map[Int, Set[Int]] = columnMapper(nodes, edges, col, nodeCols)

  println(mapped)
}
