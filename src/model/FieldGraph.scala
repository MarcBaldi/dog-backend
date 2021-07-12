package model

import scala.collection.mutable.ArrayBuffer

class FieldGraph {
  var origin: FieldNode = new FieldNode(new Position())
  var entryPoints: ArrayBuffer[FieldNode] = new ArrayBuffer[FieldNode]().addOne(origin)

  def init(): Unit = {
    origin = new FieldNode(new Position())
  }


  def addNode(): Unit = {

  }





}
