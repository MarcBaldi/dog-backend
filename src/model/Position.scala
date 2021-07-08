package model

class Position(id: Int) {
  var slot: Option[Piece] = None

  override def toString: String = id.toString
}
