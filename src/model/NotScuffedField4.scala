package model


import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class NotScuffedField4 {

  var playerPositions: mutable.Map[Pawn, Position] = new mutable.HashMap[Pawn, Position]()

  //armLength: Int = 16, playerAmount: Int = 4, pieceAmount: Int = 4

  var course: List[Position] = List.empty
  var entryPoints: List[Position] = List.empty
  var playerStartPositions: List[Position] = List.empty
  var playerEndPositions: List[Position] = List.empty

  def initArrayBuffer(): ArrayBuffer[Position] = {
    new ArrayBuffer[Position]()
  }

  def init(): Unit = {
    PawnFactory.resetPawns()
    PositionFactory.resetPosition()
    PositionFactory.createCircle()

    for (playerID <- 0 to 4) {
      val player = new Player(playerID)
      for (_ <- 0 to 4) {
        val pawn = PawnFactory.createPawn(playerID)
        val position = PositionFactory.createPosition(isStart = true)
        playerPositions.addOne(pawn, position)
      }
      PawnFactory.resetPawns()
      PositionFactory.resetPosition()
    }
  }
/*
  def reCalcView(): Unit = {
    var course = new ArrayBuffer[Position]()
    var entryPoints = new ArrayBuffer[Position]()
    var playerStartPositions: mutable.Map[Int, ArrayBuffer[Position]] = collection.mutable.Map(0 -> initArrayBuffer())
    var playerEndPositions: mutable.Map[Int, ArrayBuffer[Position]] = collection.mutable.Map(0 -> initArrayBuffer())

    for ((pawn, position) <- playerPositions) {
      if (position) {

      }
    }


  }*/
}
