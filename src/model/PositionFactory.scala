package model

import scala.collection.mutable.ArrayBuffer

object PositionFactory  {

  var currentID = 0

  def createCircle(): List[Position] = {
    val buf = new ArrayBuffer[Position]()
    for (i <- 0 to 64) {
      val pos = new Position(i, false, false)
      buf += pos
    }
    buf.toList
  }

  def createPosition(isStart: Boolean = false, isEnd: Boolean = false): Position = {
    currentID += 1
    new Position(currentID-1, isStart, isEnd)
  }

  def createStartPosition(forPlayer: Int): Position = {
    val posId = forPlayer * 16
    new Position(posId - 1, true, false)
  }

  def createEndPosition(forPlayer: Int): Position = {
    val posId = forPlayer * 16
    val res = new Position(posId + currentID, false, true)
    currentID += 1
    res
  }

  def resetPosition(): Unit = {
    currentID = 0
  }

}
