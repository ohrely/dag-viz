package viz
import graph.GraphUtil._
import OrderUtil.orderMapper

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
//    def main(canvas: html.Canvas, graph: Graph): Unit = {

    println("check1")
    /*setup*/
    val renderer = canvas.getContext("2d")
      .asInstanceOf[dom.CanvasRenderingContext2D]

    canvas.width = canvas.parentElement.clientWidth
    canvas.height = 400

    renderer.fillStyle = "#a8d8f8"
    renderer.fillRect(0, 0, canvas.width, canvas.height)

//  create map of node order
//    val orderMap = orderMapper(graph.nodes.map(_.id).toSet, graph.edges)




// iterate through order map to display nodes
    class NodeViz (id: Int, x: Int, y: Int) {
      val width: Int = 100
      val height: Int = 75
      val pad: Int = 30

      val xc: Int = x * width + x * pad
      val yc: Int = y * height + y * pad
    }

    def drawNode(id: Int, x: Int, y: Int): Unit = {
      var node = new NodeViz(id, x, y)

      renderer.fillStyle = "yellow"
      renderer.fillRect(node.xc, node.yc, node.width, node.height)
    }

    drawNode(0,0,0)


// draw edges
    class EdgeViz (edge: Edge) {

    }
  }
}
