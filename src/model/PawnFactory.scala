package model

object PawnFactory  {

  var currentID = 0

  def createPawn(player: Int): Pawn = {
    currentID += 1
    Pawn(currentID-1, player)
  }

  def resetPawns(): Unit = {
    currentID = 0
  }

}
