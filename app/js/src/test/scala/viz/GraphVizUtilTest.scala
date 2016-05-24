package viz

import org.scalatest.FunSuite
import org.apache.spark.sql.Row

/**
  * Created by rely10 on 4/15/16.
  */
class GraphVizUtilTest extends FunSuite {
  test("testy"){
    val r = Row("test1", "test2", 43)
    println(r.toSeq.map(_.toString))
  }
}
