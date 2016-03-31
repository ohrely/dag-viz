package graph

/**
  * Created by rely10 on 3/30/16.
  */

// TODO: should the parent and child attributes be mutable?
// I see possible value in not rebuilding an edge that represents an
// unchanged function just because a dependency changes.

// e.g.
// case class Edge(var parent: Node, var child: Node)


// TODO: add "name" constructor parameter

case class Edge(hash: String, parent: Node, child: Node) extends Graph
