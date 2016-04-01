package viz

import graph.GraphUtil._

/**
  * Created by rely10 on 4/1/16.
  */

object ColumnUtil {

  // If there are still nodes to add to Map:
  // 1. Set of all remaining destinations
  // 2. Find remaining nodes that are not a remaining destination (independents)
  // 3. Add independents to Map at current column #
  // 4. Remove independents from nodes list
  // 5. Remove edges with independents as a source
  // 6. Increase column #
  // 7. Repeat (recursion)
  def columnMapper(nodes: Set[Int], edges: Array[Edge],
                   col: Int, nodeCols: Map[Int,Set[Int]]): Map[Int,Set[Int]] = {

    if (nodes.isEmpty: Boolean) nodeCols else {
      val dests: Set[Int] = edges.map(_.dest).toSet
      val independents: Set[Int] = nodes -- dests

      val newNodeCols: Map[Int, Set[Int]] = nodeCols + (col -> independents)
      val newNodes: Set[Int] = nodes -- independents
      val newEdges: Array[Edge] = edges.filter(newNodes contains _.source)
      val nextCol: Int = col + 1

      columnMapper(newNodes, newEdges, nextCol, newNodeCols)
    }
  }
}
