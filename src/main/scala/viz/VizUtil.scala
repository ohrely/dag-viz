package viz

import parser.ParseUtil.parse
import graphpack.GraphUtil._
import GraphVizUtil._
import MoreInfo._

import scala.scalajs.js
import org.scalajs.dom
import org.scalajs.dom._
import org.scalajs.dom.html.{Div, Canvas}

/**
  * Created by rely10 on 3/31/16.
  */

object VizUtil extends js.JSApp {
//  TODO: will need to add passed in JSON obj back in as arg to be parsed
  def main(): Unit = {
    val mod: Element = document.getElementById("mod")
    setUp(mod)

    val graph = parse()
    showGraph(graph)
    println("there should be graph")
  }

  def setUp(mod: Element): Unit = {
    /*canvas setup*/
    val cdiv = document.createElement("div").asInstanceOf[Div]
    cdiv.id = "cdiv"
    mod.appendChild(cdiv)

    /*moreInfo space setup*/
    val tdiv = document.createElement("div").asInstanceOf[Div]
    tdiv.id = "tdiv"
    mod.appendChild(tdiv)
  }

  def showGraph(graph: Graph): Unit = {
    val c: Canvas = document.createElement("canvas").asInstanceOf[Canvas]
    dom.document.getElementById("cdiv").appendChild(c)
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
}
