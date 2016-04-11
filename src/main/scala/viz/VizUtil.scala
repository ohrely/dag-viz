package viz
import javax.swing.Renderer

import graph.GraphUtil._

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport
import org.scalajs.dom
import org.scalajs.dom.html

/**
  * Created by rely10 on 3/31/16.
  */

@JSExport
object VizUtil {
  @JSExport
  def main(canvas: html.Canvas): Unit = {
    /*setup*/
    type Ctx2D = dom.CanvasRenderingContext2D
    val renderer = canvas.getContext("2d").asInstanceOf[Ctx2D]

    canvas.width = canvas.parentElement.clientWidth
    canvas.height = 800

    renderer.fillStyle = "#a8d8f8"
    renderer.fillRect(0, 0, canvas.width, canvas.height)

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
    val graph = new GraphViz(nodes, edges)
//    end test ------------------------------------------------------------

//    val graph = ???
    drawGraph(renderer, graph)
  }

  val CWIDTH: Int = 100
  val CHEIGHT: Int = 75
  val CPAD: Int = 40

  class GraphViz(val nodes: List[Node], val edges: List[Edge]) extends Graph(nodes, edges) {
    val rows: Map[Int, List[Int]] = makeRows(nodes, edges)
    val locations: Map[Node, (Int, Int)] = applyLocations(this)
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

  def applyLocations(graph: GraphViz): Map[Node, (Int, Int)] = {
    var locationsMap = collection.mutable.Map.empty[Node, (Int, Int)]

    graph.rows.foreach {
      case (y, ynodes) => ynodes.zipWithIndex.foreach {
        case (id, x) => setLocation(id, x, y)
      }
    }

    def setLocation(id: Int, x: Int, y: Int): Unit = {
      val node: Node = graph.nodes.find(_.id == id).get
      locationsMap += (node -> (x, y))
    }

    locationsMap.toMap
  }

  def drawGraph(renderer: dom.CanvasRenderingContext2D, graph: GraphViz): Unit = {
    val nodes: List[Node] = graph.nodes
    val edges: List[Edge] = graph.edges

    nodes.foreach(node => new NodeViz(renderer, graph, node))
    edges.foreach(edge => new EdgeViz(renderer, graph, edge))
  }

  def findNode(graph: GraphViz, node: Node): (Int, Int) = graph.locations(node)

  class NodeViz (renderer: dom.CanvasRenderingContext2D, graph: GraphViz, node: Node) {
    val (x: Int, y: Int) = findNode(graph, node)
    val xc: Int = x * CWIDTH + (x + 1) * CPAD
    val yc: Int = y * CHEIGHT + (y + 1) * CPAD

    renderer.fillStyle = "white"
    renderer.fillRect(xc, yc, CWIDTH, CHEIGHT)
    renderer.font = "20px sans-serif"
    renderer.textAlign = "center"
    renderer.textBaseline = "middle"
    renderer.fillStyle = "black"
    renderer.fillText(node.props.name, (2 * xc + CWIDTH) / 2, (2 * yc+ CHEIGHT) / 2)
  }

  class EdgeViz (renderer: dom.CanvasRenderingContext2D, graph: GraphViz, edge: Edge) {
    val source: Node = graph.nodes.find(_.id == edge.source).get
    val dest: Node = graph.nodes.find(_.id == edge.dest).get

    val (sx, sy): (Int, Int) = findNode(graph, source)
    val (dx, dy): (Int, Int) = findNode(graph, dest)

    val sxc: Int = sx * CWIDTH + CWIDTH/2 + (sx + 1) * CPAD
    val syc: Int = (sy + 1) * CHEIGHT + (sy + 1) * CPAD
    val dxc: Int = dx * CWIDTH + CWIDTH/2 + (dx + 1) * CPAD
    val dyc: Int = syc + CPAD


    def drawEdge(): Unit = {
      renderer.strokeStyle = "black"
      renderer.lineWidth = 3
      renderer.beginPath()
      renderer.moveTo(sxc, syc)
      renderer.lineTo(dxc, dyc)
      //  renderer.bezierCurveTo(200, 400, 600, 400, 650, 650)
      renderer.stroke()

      renderer.font = "20px sans-serif"
      renderer.textAlign = "center"
      renderer.textBaseline = "middle"
      renderer.fillStyle = "white"
      renderer.fillText(edge.props.edgeName, (sxc + dxc) / 2, (syc + dyc) / 2)
    }

    drawEdge()
  }
}
