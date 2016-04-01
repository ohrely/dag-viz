package viz
import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport
import org.scalajs.dom
import org.scalajs.dom.html

/**
  * Created by rely10 on 3/31/16.
  */

@JSExport
object SJSViz {
  @JSExport
  def main(canvas: html.Canvas): Unit = {

    /*setup*/
    val renderer = canvas.getContext("2d")
      .asInstanceOf[dom.CanvasRenderingContext2D]

    canvas.width = canvas.parentElement.clientWidth
    canvas.height = 400




  }
}
