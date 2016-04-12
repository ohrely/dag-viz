package viz
import VizUtil._
import graph.GraphUtil._
import org.scalajs.dom
import org.scalajs.dom.html

/**
  * Created by rely10 on 4/11/16.
  */
object MoreInfo {
  def showMore (ctx: dom.CanvasRenderingContext2D, g: GraphViz, node: Node): Unit = {
    ctx.font = "20px sans-serif"
    ctx.textAlign = "center"
    ctx.textBaseline = "middle"
    ctx.fillStyle = "black"
    ctx.fillText(node.props.name, g.rightSpace + CPAD, 100)
  }
}