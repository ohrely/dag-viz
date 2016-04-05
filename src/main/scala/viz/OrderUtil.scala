package viz

import graph.GraphUtil._

/**
  * Created by rely10 on 4/1/16.
  */

object OrderUtil {

  // If there are still nodes to add to Map:
  // 1. Set of all remaining destinations
  // 2. Find remaining nodes that are not a remaining destination (independents)
  // 3. Add independents to Map at current tracker #
  // 4. Remove independents from nodes list
  // 5. Remove edges with independents as a source
  // 6. Increase tracker #
  // 7. Repeat (recursion)

  //  TODO: should we test for nodes with no connections before running orderMapper?  What would such nodes represent?
  def orderMapper(nodes: Set[Int], edges: Array[Edge],
                  i: Int = 0, nodeOrders: Map[Int,Set[Int]] = Map()): Map[Int,Set[Int]] = {

    if (nodes.isEmpty: Boolean) nodeOrders else {
      val dests: Set[Int] = edges.map(_.dest).toSet
      val independents: Set[Int] = nodes -- dests

      val newNodeOrders: Map[Int, Set[Int]] = nodeOrders + (i -> independents)
      val newNodes: Set[Int] = nodes -- independents
      val newEdges: Array[Edge] = edges.filter(newNodes contains _.source)
      val nexti: Int = i + 1

      orderMapper(newNodes, newEdges, nexti, newNodeOrders)
    }
  }
}
