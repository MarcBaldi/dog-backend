package model

import scala.collection.mutable

class GameData(val armLength: Int = 15, val playerCount: Int = 4, val pieceAmount: Int = 4)  {

  var currentPlayer: Int = 0
  var currentRound: Int = 0
  val finishedPlayers: mutable.ArrayBuffer[Int] = mutable.ArrayBuffer()
  val enableUndo: Boolean = false

  // state enum
  var gameState: GameState.Value = GameState.init
}
