package model

import model.FieldType.FieldType

object FieldNodeFactory  {

  var currentID = 0

  def createFieldNode(): FieldNode = {
    currentID += 1
    FieldNode(currentID, "")
  }

  // TODO: deprecated, remove description tag
  def createFieldNode(description: String): FieldNode = {
    currentID += 1
    FieldNode(currentID, description)
  }

  def createFieldNode(description: String, fieldType: FieldType): FieldNode = {
    currentID += 1
    FieldNode(currentID, description)
  }

  def resetPawns(): Unit = {
    currentID = 0
  }

}
