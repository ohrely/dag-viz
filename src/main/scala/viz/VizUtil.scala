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
    val graph: Graph = Graph(nodes, edges)
//    end test ------------------------------------------------------------

//    val graph = ???
    drawGraph(renderer, graph)
  }

  val CWIDTH: Int = 100
  val CHEIGHT: Int = 75
  val CPAD: Int = 40

  def drawGraph(renderer: dom.CanvasRenderingContext2D, graph: Graph): Unit = {
    val nodes: List[Node] = graph.nodes
    val edges: List[Edge] = graph.edges

    nodes.foreach(node => new NodeViz(renderer, node))
    edges.foreach(edge => new EdgeViz(renderer, graph, edge))
  }

  class NodeViz (renderer: dom.CanvasRenderingContext2D, node: Node) {
    val x: Int = node.props.x
    val y: Int = node.props.y
    val xc: Int = x * CWIDTH + (x + 1) * CPAD
    val yc: Int = y * CHEIGHT + (y + 1) * CPAD

    val color = "white"
    renderer.fillStyle = color
    renderer.fillRect(xc, yc, CWIDTH, CHEIGHT)
    renderer.font = "20px sans-serif"
    renderer.textAlign = "center"
    renderer.textBaseline = "middle"
    renderer.fillStyle = "black"
    renderer.fillText(node.props.name, (2 * xc + CWIDTH) / 2, (2 * yc+ CHEIGHT) / 2)
  }

  class EdgeViz (renderer: dom.CanvasRenderingContext2D, graph: Graph, edge: Edge) {
    val source: Node = graph.nodes.find(_.id == edge.source).get
    val dest: Node = graph.nodes.find(_.id == edge.dest).get

    val sx: Int = source.props.x
    val sy: Int = source.props.y
    val dx: Int = dest.props.x
    val dy: Int = dest.props.y

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
