package model

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object PawnFactory  {

  private val allPawns: ArrayBuffer[Pawn] = new mutable.ArrayBuffer[Pawn]
  var currentID = 0

  def createPawn(player: Int): Pawn = {
    currentID += 1
    val pawn = Pawn(currentID-1, player)
    allPawns.addOne(pawn)
    pawn
  }

  def createPawn(player: Player): Pawn = {
    currentID += 1
    val pawn = Pawn(currentID-1, player.id)
    allPawns.addOne(pawn)
    pawn
  }

  def resetPawns(resetCollection: Boolean = false): Unit = {
    currentID = 0
    if (resetCollection)
      allPawns.clear()
  }

  def getAllPawns: ArrayBuffer[Pawn] = allPawns
}
