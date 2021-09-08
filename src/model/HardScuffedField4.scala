package model

import scala.collection.mutable

class HardScuffedField4 {

  // Pawn <- Position
  var playerPositions: mutable.Map[Pawn, Int] = new mutable.HashMap[Pawn, Int]()
  // Player <- Position
  var startPositions: mutable.Map[Int, Int] = new mutable.HashMap[Int, Int]()

  def init(playerAmount: Int = 4, pieceAmount: Int = 4): Unit = {
    for (i <- 0 to playerAmount) {
      PawnFactory.resetPawns()

      for (j <- 0 until pieceAmount) {
        val positionId = (i + 1) * 100 + j
        val pawn = PawnFactory.createPawn(i)
        playerPositions += ((pawn, positionId))
      }

      startPositions += ((i, i * 16))
    }

  }

  def reCalcView(): Unit = {

  }
}
