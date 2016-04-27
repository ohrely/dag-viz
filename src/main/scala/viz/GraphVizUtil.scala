package viz

import graphpack.GraphUtil._
import scala.collection.mutable.{Map => MMap}
import org.scalajs.dom

/**
  * Created by rely10 on 4/13/16.
  */
object GraphVizUtil {

  /*change these to safely alter size & spacing of graphpack elements*/
  val CWIDTH: Int = 100
  val CHEIGHT: Int = 75
  val CPAD: Int = 40

  class GraphViz(g: Graph) {
    val nodes: List[Node] = g.nodes
    val edges: List[Edge] = g.edges

    val rows: Map[Int, List[Int]] = makeRows(nodes, edges)
    val locations: Map[Node, (Int, Int)] = applyLocations(this)
    val numCols = rows.valuesIterator.reduceLeft((a, b) => if (a.length > b.length) a else b).length
    val numRows = rows.keysIterator.reduceLeft((a, b) => if (a > b) a else b) + 1
    val nodeTracker = MMap.empty[(Int, Int), MMap[(Int, Int), Node]]

    val width = numCols * CWIDTH + (numCols + 1) * CPAD
    val height = numRows * CHEIGHT + (numRows + 1) * CPAD
  }

  def makeRows(nodes: List[Node], edges: List[Edge]): Map[Int, List[Int]] = {
    /*recursively determine location of nodes in graphpack visualization*/
//    TODO: prevent edge crossing
    val emptyRows = Map.empty[Int, List[Int]]
    var i: Int = 0

    var nodesToMap: Set[Int] = nodes.map(_.id).toSet
    var edgesToMap: Set[Edge] = edges.toSet

    def mapRows(rows: Map[Int, List[Int]]): Map[Int, List[Int]] = {
      // If there are still nodes to add to Map:
      // 1. Set of all remaining destinations
      // 2. Find remaining nodes that are not a remaining destination (independents)
      // 3. Add independents to Map at current tracker #
      // 4. Remove independents from nodes list
      // 5. Remove edges with independents as a source
      // 6. Increase tracker #
      // 7. Repeat (recursion)
      //  TODO: should we test for nodes with no connections before running? What would such nodes represent?

      if (nodesToMap.isEmpty: Boolean) rows else {
        val dests: Set[Int] = edgesToMap.map(_.dest)
        val independents: Set[Int] = nodesToMap -- dests

        val newRows = rows + (i -> independents.toList)
        nodesToMap = nodesToMap -- independents
        edgesToMap = edgesToMap.filter(nodesToMap contains _.source)
        i += 1

        mapRows(newRows)
      }
    }

    mapRows(emptyRows)
  }

  def applyLocations(g: GraphViz): Map[Node, (Int, Int)] = {
    /*create handy map of nodes as keys and grid coordinates as values*/
    //TODO: Make this a non-mutable structure
    val locationsMap = MMap.empty[Node, (Int, Int)]

    g.rows.foreach {
      case (y, ynodes) => ynodes.zipWithIndex.foreach {
        case (id, x) => setLocation(id, x, y)
      }
    }

    def setLocation(id: Int, x: Int, y: Int): Unit = {
      val node: Node = g.nodes.find(_.id == id).get
      locationsMap += (node -> (x, y))
    }

    locationsMap.toMap
  }

  def drawGraph(ctx: dom.CanvasRenderingContext2D, g: GraphViz): Unit = {
    val nodes: List[Node] = g.nodes
    val edges: List[Edge] = g.edges

    nodes.foreach(node => new NodeViz(ctx, g, node))
    edges.foreach(edge => new EdgeViz(ctx, g, edge))

//    TODO: is there a better place to put this?
    g.nodeTracker.toMap
  }

  def findNode(graph: GraphViz, node: Node): (Int, Int) = graph.locations(node)

  def trackNode(node: Node, xc: Int, yc: Int, nodeTracker: MMap[(Int, Int), MMap[(Int, Int), Node]]): Unit = {
    /*add a node to the nodeTracker - mapped to coordinates*/
    val yMap = nodeTracker.getOrElseUpdate((yc, yc + CHEIGHT), MMap.empty[(Int, Int), Node])
    yMap += ((xc, xc + CWIDTH) -> node)
  }

  class NodeViz (ctx: dom.CanvasRenderingContext2D, g: GraphViz, node: Node) {
    /*calculate coordinates, draw a node on the canvas, track where it was drawn*/

    val (x: Int, y: Int) = findNode(g, node)
    val xc: Int = x * CWIDTH + (x + 1) * CPAD
    val yc: Int = y * CHEIGHT + (y + 1) * CPAD

    def drawNode(): Unit = {
      ctx.fillStyle = "white"
      ctx.fillRect(xc, yc, CWIDTH, CHEIGHT)
      ctx.font = "20px sans-serif"
      ctx.textAlign = "center"
      ctx.textBaseline = "middle"
      ctx.fillStyle = "black"
      ctx.fillText(node.props.name, (2 * xc + CWIDTH) / 2, (2 * yc + CHEIGHT) / 2)
    }

    drawNode()
    trackNode(node, xc, yc, g.nodeTracker)
  }

  class EdgeViz (ctx: dom.CanvasRenderingContext2D, g: GraphViz, edge: Edge) {
    /*calculate coordinates and draw an edge on the canvas*/

    val source: Node = g.nodes.find(_.id == edge.source).get
    val dest: Node = g.nodes.find(_.id == edge.dest).get

    val (sx, sy): (Int, Int) = findNode(g, source)
    val (dx, dy): (Int, Int) = findNode(g, dest)

    val sxc: Int = sx * CWIDTH + CWIDTH/2 + (sx + 1) * CPAD
    val syc: Int = (sy + 1) * CHEIGHT + (sy + 1) * CPAD
    val dxc: Int = dx * CWIDTH + CWIDTH/2 + (dx + 1) * CPAD
    val dyc: Int = dy * CHEIGHT + (dy + 1) * CPAD


    def drawEdge(): Unit = {
      ctx.strokeStyle = "black"
      ctx.lineWidth = 3
      ctx.beginPath()
      ctx.moveTo(sxc, syc)
//      TODO: could be cuter
      ctx.bezierCurveTo(sxc, dyc, dxc, dyc - CPAD, dxc, dyc)
      ctx.stroke()

      ctx.font = "20px sans-serif"
      ctx.textAlign = "center"
      ctx.textBaseline = "middle"
      ctx.fillStyle = "white"
      ctx.fillText(edge.props.edgeName, (sxc + dxc)/2, dyc - CPAD/2)
    }

    drawEdge()
  }
}
