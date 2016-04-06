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
    val renderer = canvas.getContext("2d")
      .asInstanceOf[dom.CanvasRenderingContext2D]

    canvas.width = canvas.parentElement.clientWidth
    canvas.height = 800

    renderer.fillStyle = "#a8d8f8"
    renderer.fillRect(0, 0, canvas.width, canvas.height)

//    test values
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

    drawGraph(renderer, graph)
//    val a = new NodeViz(0,0,0)
//    val b = new NodeViz(1,1,1)
//    val c = new NodeViz(2,0,1)
//
//    a.drawNode(renderer)
//    b.drawNode(renderer)
//    c.drawNode(renderer)
  }

  // iterate through order map to display nodes
  def drawGraph(renderer: dom.CanvasRenderingContext2D, graph: Graph): Unit = {
//    draw nodes
      val rows: Map[Int, List[Int]] = graph.rows
      rows.foreach {
        case(y, ynodes) => ynodes.zipWithIndex.foreach{
          case(id, x) => new NodeViz(renderer, id, x, y)
        }

//    drawNodes()
    }

//    rows foreach {
//      (y: Int, ynodes: List[Int]) => ynodes.view.zipWithIndex.foreach {
//        case (id: Int, index: Int) => new NodeViz(id, index, y)
//      }
//    }

  }


  class NodeViz (renderer: dom.CanvasRenderingContext2D, id: Int, x: Int, y: Int) {
    val width: Int = 100
    val height: Int = 75
    val pad: Int = 30

    val xc: Int = x * width + (x + 1) * pad
    val yc: Int = y * height + (y + 1) * pad

    drawNode(renderer)

    def drawNode(renderer: dom.CanvasRenderingContext2D, color: String = "white"): Unit = {

      renderer.fillStyle = color
      renderer.fillRect(xc, yc, width, height)
    }
  }

  class EdgeViz (edgeObj: Edge) {
    val edge: Edge = edgeObj
    val source: Int = edge.source
    val dest: Int = edge.dest

    def drawEdge(renderer: dom.CanvasRenderingContext2D): Unit = {
      renderer.fillStyle = "black"
    }
  }
}
