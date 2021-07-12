package model

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class HardScuffedField4 {
  var course = new ArrayBuffer[Position]()
  var entryPoints = new ArrayBuffer[Position]()

  var playerStartPositions: mutable.Map[Int, ArrayBuffer[Position]] = collection.mutable.Map(0 -> initArrayBuffer())
  var playerEndPositions: mutable.Map[Int, ArrayBuffer[Position]] = collection.mutable.Map(0 -> initArrayBuffer())

  def initArrayBuffer(): ArrayBuffer[Position] = {
    new ArrayBuffer[Position]()
  }

  def init(armLength: Int = 16, playerAmount: Int = 4, pieceAmount: Int = 4): Unit = {
    for (i <- 0 until armLength * playerAmount) {
      val position = new Position(i)
      course += position

      if (i % armLength == 0)
        entryPoints += position
    }

    for (i <- 0 to playerAmount) {
      val playerStartPositionsTmp = new ArrayBuffer[Position]()
      val playerEndPositionsTmp = new ArrayBuffer[Position]()

      for (j <- 0 until pieceAmount) {
        val position = new Position(j)
        position.slot = Some(new Piece(j, new Player(i)))
        val position2 = new Position(j)
        playerStartPositionsTmp += position
        playerEndPositionsTmp += position2
      }

      playerStartPositions += (i -> playerStartPositionsTmp)
      playerEndPositions += (i -> playerEndPositionsTmp)
    }
  }
}
