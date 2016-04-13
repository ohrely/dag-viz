package viz
import VizUtil._
import graph.GraphUtil._
import GraphVizUtil._
import org.scalajs.dom
import org.scalajs.dom.html
import org.scalajs.dom.html.{Div, Table}

/**
  * Created by rely10 on 4/11/16.
  */
object MoreInfo {
  def moreNode (tdiv: Div, g: GraphViz, node: Node): Unit = {
    val t = dom.document.createElement("table").asInstanceOf[Table]

//    put node data into table

    tdiv.appendChild(t)
  }
}