package model

case class Pawn(id: Int, player: Int)  {
  override def toString: String = player.toString ++ "-" ++ id.toString

  def equals(pawn: Pawn): Boolean = {
    if (pawn.id == this.id && pawn.player == this.player)
      return true
    false
  }
}
