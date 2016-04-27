package viz

import graphpack.TestGraph.test_graph
import graphpack.GraphUtil._
import GraphVizUtil._
import MoreInfo._

import scala.scalajs.js.annotation.JSExport
import org.scalajs.dom
import org.scalajs.dom._
import org.scalajs.dom.html.{Div, Canvas}

/**
  * Created by rely10 on 3/31/16.
  */

@JSExport
object VizUtil {
  @JSExport
  def main(graph: Graph = test_graph, mod: Element = document.getElementById("mod")): Unit = {
    showGraph(graph, mod)
    showMore(mod)
  }

  @JSExport
  def showGraph(graph: Graph, mod: Element): Unit = {
    /*canvas setup*/
    val c = dom.document.createElement("canvas").asInstanceOf[Canvas]
    mod.appendChild(c)
    val ctx = c.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

    val g = new GraphViz(graph)

    c.width = g.width
    c.height = g.height

    ctx.fillStyle = "#a8d8f8"
    ctx.fillRect(0, 0, c.width, c.height)

    drawGraph(ctx, g)

    /*add listener to display data of element user clicks on*/
    c.onclick = (e: dom.MouseEvent) => {
      handleClick(g, e)
    }
  }

  @JSExport
  def showMore(mod: Element): Unit = {
    /*moreInfo space setup*/
    val tdiv = dom.document.createElement("div")
    tdiv.id = "tdiv"
    mod.appendChild(tdiv)
  }
}
