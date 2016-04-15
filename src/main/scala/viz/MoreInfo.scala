package viz

import graph.GraphUtil._
import GraphVizUtil._

import org.scalajs.dom
import scala.scalajs.js.annotation.JSExport

import scalatags.JsDom._
import scalatags._
import scalatags.Text.all._

//import org.scalajs.dom.html
import org.scalajs.dom.html.{Div, Table}

/**
  * Created by rely10 on 4/11/16.
  */

@JSExport
object MoreInfo {
//  @JSExport
//  def moreNode (tdiv: Div, g: GraphViz, node: Node): Unit = {
//    val nodeName: String = node.props.name
//    val nodeData: Map = ???
//
//    val t = dom.document.createElement("table").asInstanceOf[Table]
////    put rows into table
////    https://github.com/scala-js/scala-js-dom/blob/master/src/main/scala/org/scalajs/dom/raw/Html.scala
////    t.caption = nodeName
//    t.tHead = ???
//    t.insertRow(-1)
//
//    tdiv.appendChild(t)
//  }

  def handleClick(g: GraphViz, click: dom.MouseEvent): Unit = {
    val clickX: Int = click.clientX.toInt
    val clickY: Int = click.clientY.toInt

    /*find click in nodeTracker*/
    val ySlots = g.nodeTracker.keys

    if (ySlots.exists(slot => (slot._1 <= clickY) && (clickY <= slot._2))) {
      val ySlot: (Int, Int) = ySlots.find(slot => (slot._1 <= clickY) && (clickY <= slot._2)).get

      val xSlots = g.nodeTracker(ySlot).keys

      if (xSlots.exists(slot => (slot._1 <= clickX) && (clickX <= slot._2))) {
        val xSlot: (Int, Int) = xSlots.find(slot => (slot._1 <= clickX) && (clickX <= slot._2)).get

        val clicked: Node = g.nodeTracker(ySlot)(xSlot)

        showData(clicked)
      }
    }
  }

  @JSExport
  def showData(node: Node): Unit = {
//    TODO: display node data table to right of graph

    val t = {
      table(
        caption(node.props.name),
        thead(
          tr(
           td("Head1"),
           td("Head2")
          )
        ),
        tbody(
          tr(
            td("Yes"),
            td("Maybe")
          ),
          tr(
            td("Yes"),
            td("Maybe")
          ),
          tr(
            td("Yes"),
            td("Maybe")
          )
        )
      )
    }

    dom.document.getElementById("tdiv").innerHTML = t.render
  }

}