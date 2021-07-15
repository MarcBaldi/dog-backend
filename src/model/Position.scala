package model

class Position(id: Int = -1, isStart: Boolean = false, isEnd: Boolean = false) {
  var slot: Option[Pawn] = None


  override def toString: String = {
    slot match {
      case Some(b) =>
        "(" + b.id + ")"
      case None =>
        id.toString

    }
  }
}
