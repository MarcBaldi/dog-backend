package model

import scala.collection.mutable.ArrayBuffer

class FieldNode(position: Position) {

  var next: ArrayBuffer[FieldNode] = new ArrayBuffer[FieldNode]()

  def toImmutable: FieldNodeFinal = {
    FieldNodeFinal(position, next.toList)
  }


}
