package viz
import javax.swing.Renderer
import graph.GraphUtil._
//import OrderUtil.orderMapper

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
    canvas.height = 400

    renderer.fillStyle = "#a8d8f8"
    renderer.fillRect(0, 0, canvas.width, canvas.height)

//  create map of node order
    //    val graph: Graph = ???
//    val orderMap = orderMapper(graph.nodes.map(_.id).toSet, graph.edges)



// iterate through order map to display nodes

//    val a = new NodeViz(0,0,0)
//    val b = new NodeViz(1,1,1)
//    val c = new NodeViz(2,0,1)
//
//    a.drawNode("red", renderer)
//    b.drawNode("orange", renderer)
//    c.drawNode("yellow", renderer)

  }

//  def drawGraph(graph: Graph): Unit = {
//    val rows: Map[Int, List[Int]] = graph.rows
//    rows foreach {
//      (y: Int, ynodes: List[Int]) => ynodes.view.zipWithIndex.foreach {
//        case (id: Int, index: Int) => new NodeViz(id, index, y)
//      }
//    }
//
//  }



  class NodeViz (id: Int, x: Int, y: Int) {
    val width: Int = 100
    val height: Int = 75
    val pad: Int = 30

    val xc: Int = x * width + (x + 1) * pad
    val yc: Int = y * height + (y + 1) * pad

    def drawNode(color: String = "white", renderer: dom.CanvasRenderingContext2D): Unit = {
      renderer.fillStyle = color
      renderer.fillRect(xc, yc, width, height)
    }
  }


  // draw edges
  class EdgeViz (edgeObj: Edge) {
    val edge: Edge = edgeObj
    val source: Int = edge.source
    val dest: Int = edge.dest

    def drawEdge(renderer: dom.CanvasRenderingContext2D): Unit = {
      renderer.fillStyle = "black"
    }
  }
}
