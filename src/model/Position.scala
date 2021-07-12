package model

class Position(id: Int = -1) {
  var slot: Option[Piece] = None

  override def toString: String = {
    slot match {
      case Some(b) =>
        "(" + b.id + ")"
      case None =>
        id.toString

    }
  }
}
