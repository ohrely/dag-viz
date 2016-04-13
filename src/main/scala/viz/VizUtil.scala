package viz
import javax.swing.Renderer

import graph.GraphUtil._
import MoreInfo._

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport
import org.scalajs.dom
import org.scalajs.dom.html.{Div, Canvas}
import scalatags.JsDom.all._

/**
  * Created by rely10 on 3/31/16.
  */

@JSExport
object VizUtil {
  @JSExport
  def main(mod: Div): Unit = {
    showGraph(mod)
  }

  def showGraph(mod: Div): Unit = {
    /*setup*/
    val c = dom.document.createElement("canvas").asInstanceOf[Canvas]
    mod.appendChild(c)
    println("check2")
    val ctx = c.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
    println("check3")

//    test ----------------------------------------------------------------
    val nodes = (0 to 7).toList.map(i => Node(i, NodeProps(i.toString)))
    val edges = List(
      Edge(11, 0, 3, EdgeProps("yes")),
      Edge(12, 1, 4, EdgeProps("yes")),
      Edge(13, 2, 4, EdgeProps("yes")),
      Edge(21, 3, 5, EdgeProps("yes")),
      Edge(22, 4, 6, EdgeProps("yes")),
      Edge(31, 5, 7, EdgeProps("yes")),
      Edge(32, 6, 7, EdgeProps("yes"))
    )
    val g = new GraphViz(nodes, edges)

    c.width = g.width
    c.height = g.height

    ctx.fillStyle = "#a8d8f8"
    ctx.fillRect(0, 0, c.width, c.height)

    drawGraph(ctx, g)

//    ctx.fillStyle = "#ffff99"
//    ctx.fillRect(g.rightSpace, 0, c.width - g.rightSpace, c.height)

    val newNodes = (0 to 3).toList.map(i => Node(i, NodeProps(i.toString)))
    val newEdges = List(
      Edge(11, 0, 2, EdgeProps("yes")),
      Edge(12, 1, 2, EdgeProps("yes")),
      Edge(13, 2, 3, EdgeProps("yes"))
    )
    val newGraph = new GraphViz(newNodes, newEdges)

    c.onclick = (e: dom.MouseEvent) => {
      c.width = newGraph.width
      c.height = newGraph.height

      ctx.fillStyle = "#a8d8f8"
      ctx.fillRect(0, 0, c.width, c.height)
      drawGraph(ctx, newGraph)
    }

//    showMore(ctx, g, g.nodes(2))
  }

  val CWIDTH: Int = 100
  val CHEIGHT: Int = 75
  val CPAD: Int = 40

  class GraphViz(val nodes: List[Node], val edges: List[Edge]) extends Graph(nodes, edges) {
    val rows: Map[Int, List[Int]] = makeRows(nodes, edges)
    val locations: Map[Node, (Int, Int)] = applyLocations(this)
    val numCols = rows.valuesIterator.reduceLeft((a, b) => if (a.length > b.length) a else b).length
    val numRows = rows.keysIterator.reduceLeft((a, b) => if (a > b) a else b) + 1
    val width = numCols * CWIDTH + (numCols + 1) * CPAD
    val height = numRows * CHEIGHT + (numRows + 1) * CPAD
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

  def applyLocations(g: GraphViz): Map[Node, (Int, Int)] = {
    var locationsMap = collection.mutable.Map.empty[Node, (Int, Int)]

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
  }

  def findNode(graph: GraphViz, node: Node): (Int, Int) = graph.locations(node)

  class NodeViz (ctx: dom.CanvasRenderingContext2D, g: GraphViz, node: Node) {
    val (x: Int, y: Int) = findNode(g, node)
    val xc: Int = x * CWIDTH + (x + 1) * CPAD
    val yc: Int = y * CHEIGHT + (y + 1) * CPAD

    ctx.fillStyle = "white"
    ctx.fillRect(xc, yc, CWIDTH, CHEIGHT)
    ctx.font = "20px sans-serif"
    ctx.textAlign = "center"
    ctx.textBaseline = "middle"
    ctx.fillStyle = "black"
    ctx.fillText(node.props.name, (2 * xc + CWIDTH) / 2, (2 * yc+ CHEIGHT) / 2)
  }

  class EdgeViz (ctx: dom.CanvasRenderingContext2D, g: GraphViz, edge: Edge) {
    val source: Node = g.nodes.find(_.id == edge.source).get
    val dest: Node = g.nodes.find(_.id == edge.dest).get

    val (sx, sy): (Int, Int) = findNode(g, source)
    val (dx, dy): (Int, Int) = findNode(g, dest)

    val sxc: Int = sx * CWIDTH + CWIDTH/2 + (sx + 1) * CPAD
    val syc: Int = (sy + 1) * CHEIGHT + (sy + 1) * CPAD
    val dxc: Int = dx * CWIDTH + CWIDTH/2 + (dx + 1) * CPAD
    val dyc: Int = syc + CPAD


    def drawEdge(): Unit = {
      ctx.strokeStyle = "black"
      ctx.lineWidth = 3
      ctx.beginPath()
      ctx.moveTo(sxc, syc)
      ctx.bezierCurveTo(sxc, dyc, dxc, syc, dxc, dyc)
      ctx.stroke()

      ctx.font = "20px sans-serif"
      ctx.textAlign = "center"
      ctx.textBaseline = "middle"
      ctx.fillStyle = "white"
      ctx.fillText(edge.props.edgeName, (sxc + dxc) / 2, (syc + dyc) / 2)
    }

    drawEdge()
  }
}
