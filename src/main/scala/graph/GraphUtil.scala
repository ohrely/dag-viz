package graph

/**
  * Created by rely10 on 3/31/16.
  */
object GraphUtil {
  case class Edge(id: Int, source: Int, dest: Int, props: EdgeProps)
  case class Node(id: Int, props: NodeProps)

  case class NodeProps(name: String)
  case class EdgeProps(edgeName: String)

//  case class Graph(nodes: List[Node], edges: List[Edge])
}

