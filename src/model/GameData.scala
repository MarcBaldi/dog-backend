package model

import com.typesafe.scalalogging.Logger

import scala.collection.mutable

class GameData(val armLength: Int = 15, val playerCount: Int = 4, val pieceAmount: Int = 4)  {

  val logger: Logger = Logger("GameData")

  var currentPlayer: Int = 0
  var currentRound: Int = 0
  val finishedPlayers: mutable.ArrayBuffer[Int] = mutable.ArrayBuffer()
  val enableUndo: Boolean = false
  val playerColors: mutable.Map[Int, String] = new mutable.HashMap()
  val defaultArray: List[String] = scala.collection.immutable.List("Red", "Green", "Blue", "Yellow")
  this.setColors(defaultArray)

  // state enum
  var gameState: GameState.Value = GameState.init

  def setColors(colorArray: List[String]): Unit = {
    var player = 0
    for (col<- colorArray) {
      this.playerColors += ((player, col))
      player+=1
    }
  }

  def nextPlayerTurn(): Unit = {
    this.currentPlayer = (this.currentPlayer + 1) % this.playerCount
  }
}
