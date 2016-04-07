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

  def drawGraph(renderer: dom.CanvasRenderingContext2D, graph: Graph): Unit = {
    val nodes: List[Node] = graph.nodes
    val edges: List[Edge] = graph.edges

    nodes.foreach(node => new NodeViz(renderer, node))
    edges.foreach(edge => new EdgeViz(renderer, graph, edge))
  }

  class NodeViz (renderer: dom.CanvasRenderingContext2D, node: Node) {
    val width: Int = 100
    val height: Int = 75
    val pad: Int = 30

    val x: Int = node.props.x
    val y: Int = node.props.y
    val xc: Int = x * width + (x + 1) * pad
    val yc: Int = y * height + (y + 1) * pad

    val color = "white"
    renderer.fillStyle = color
    renderer.fillRect(xc, yc, width, height)
  }

  class EdgeViz (renderer: dom.CanvasRenderingContext2D, graph: Graph, edge: Edge) {
    val source: Int = edge.source
    val dest: Int = edge.dest

    def drawEdge(renderer: dom.CanvasRenderingContext2D): Unit = {
      renderer.strokeStyle = "black"
      renderer.lineWidth = 3
      renderer.beginPath()
      renderer.moveTo(100, 100)
      renderer.lineTo(500, 500)
      renderer.moveTo(150, 150)
      renderer.bezierCurveTo(200, 400, 600, 400, 650, 650)
      renderer.stroke()
    }
  }
}
