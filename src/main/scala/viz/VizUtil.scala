package viz
import javax.swing.Renderer

import graph.GraphUtil._
import GraphVizUtil._
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
    showMore(mod)
  }

  @JSExport
  def showGraph(mod: Div): Unit = {
    /*setup*/
    val c = dom.document.createElement("canvas").asInstanceOf[Canvas]
    mod.appendChild(c)
    val ctx = c.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

//    test ----------------------------------------------------------------
    val nodes = (0 to 7).toList.map(i => Node(i, NodeProps(i.toString)))
    val edges = List(
      Edge(11, 0, 3, EdgeProps("yes")),
      Edge(12, 1, 4, EdgeProps("yes")),
      Edge(13, 2, 6, EdgeProps("yes")),
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
  }

  @JSExport
  def showMore(mod: Div): Unit = {
    val tdiv = dom.document.createElement("div")
    tdiv.id = "tdiv"
    mod.appendChild(tdiv)
  }
}
