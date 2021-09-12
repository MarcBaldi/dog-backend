package model

import model.FieldType.FieldType

object FieldNodeFactory  {
  var currentID = 0

  def createFieldNode(): FieldNode = {
    currentID += 1
    FieldNode(currentID)
  }

  def createFieldNode(fieldType: FieldType): FieldNode = {
    currentID += 1
    val res = FieldNode(currentID)
    res.fieldType = fieldType
    res
  }

  def resetPawns(): Unit = {
    currentID = 0
  }
}
