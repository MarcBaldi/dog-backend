package model

case class FieldNode(id: Int, description: String) {

  override def toString: String = id + "-" + description
}
