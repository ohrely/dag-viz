package graph

/**
  * Created by rely10 on 3/31/16.
  */

object GraphUtil {
  case class Node(id: Int, props: NodeProps)
  case class Edge(id: Int, source: Int, dest: Int, props: EdgeProps)

  case class NodeProps(name: String) {
    val x: Int = _
    val y: Int = _
  }
  case class EdgeProps(edgeName: String)

  case class Graph(nodes: List[Node], edges: List[Edge]) {
    val rows: Map[Int, List[Int]] = makeRows(nodes, edges)
    applyLocations(this)
  }

  def makeRows(nodes: List[Node], edges: List[Edge]): Map[Int, List[Int]] = {
    var rows = collection.mutable.Map.empty[Int, List[Int]]
    var i: Int = 0

    var nodesToMap: Set[Int] = nodes.map(_.id).toSet
    var edgesToMap: Set[Edge] = edges.toSet

    def mapRows(rows: collection.mutable.Map[Int, List[Int]]): Map[Int, List[Int]] = {
      // If there are still nodes to add to Map:
      // 1. Set of all remaining destinations
      // 2. Find remaining nodes that are not a remaining destination (independents)
      // 3. Add independents to Map at current tracker #
      // 4. Remove independents from nodes list
      // 5. Remove edges with independents as a source
      // 6. Increase tracker #
      // 7. Repeat (recursion)
      //  TODO: should we test for nodes with no connections before running? What would such nodes represent?

      if (nodesToMap.isEmpty: Boolean) rows.toMap else {
        val dests: Set[Int] = edgesToMap.map(_.dest)
        val independents: Set[Int] = nodesToMap -- dests

        rows += (i -> independents.toList)
        nodesToMap = nodesToMap -- independents
        edgesToMap = edgesToMap.filter(nodesToMap contains _.source)
        i += 1

        mapRows(rows)
      }
    }

    mapRows(rows)
  }

  def applyLocations(graph: Graph): Unit = {
    graph.rows.foreach {
      case (y, ynodes) => ynodes.zipWithIndex.foreach {
        case (id, x) => setLocation(id, x, y)
      }
    }

    def setLocation(id: Int, x: Int, y: Int): Unit = {
      val node: Node = graph.nodes.find(_.id == id).get
      val node.props.x = x
      val node.props.y = y
    }
  }

}