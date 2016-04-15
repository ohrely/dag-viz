package viz

import graph.GraphUtil.Node
import GraphVizUtil._

import scala.collection.mutable.{Map => MMap}
import scala.scalajs.js.annotation.JSExport

//import graph.GraphUtil._
//import VizUtil._
//import GraphVizUtil._
//import org.scalajs.dom
//import org.scalajs.dom.html
//import org.scalajs.dom.html.{Div, Table}

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

  def findClick(g: GraphViz, click: ???): Unit = {
    val clickX: Int = ???
    val clickY: Int = ???

    val ySlots = g.nodeTracker.keys

    if (ySlots.exists(slot => (slot._1 <= clickY) && (clickY <= slot._2))) {
      val ySlot: (Int, Int) = ySlots.find(slot => (slot._1 <= clickY) && (clickY <= slot._2)).get

      val xSlots = g.nodeTracker(ySlot).keys

      if (xSlots.exists(slot => (slot._1 <= clickX) && (clickX <= slot._2))) {
        val xSlot: (Int, Int) = xSlots.find(slot => (slot._1 <= clickX) && (clickX <= slot._2)).get

        val clicked: Node = g.nodeTracker(ySlot)(xSlot)

        showNode(clicked)
      }
    }
  }

  def showNode(node: Node): Unit = {

  }

}