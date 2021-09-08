package model

object FieldNodeFactory  {

  var currentID = 0

  def createFieldNode(): FieldNode = {
    currentID += 1
    FieldNode(currentID, "")
  }

  def createFieldNode(description: String): FieldNode = {
    currentID += 1
    FieldNode(currentID, description)
  }

  def resetPawns(): Unit = {
    currentID = 0
  }

}
