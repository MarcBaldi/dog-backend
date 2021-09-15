package controller

import model.{GameData, GameState}

class FlowController(val gameData: GameData) {

  def init(): Unit = {

  }

  def start(): Unit = {


  }

  def gameLoop(): Unit = {
    while (true) {
      this.gameTick()
    }
  }

  def gameTick(): Unit = {
    gameData.gameState match  {
      case GameState.init => this.handleInit()
      case GameState.preRound => this.handleRound()
      case GameState.chooseCard => this.handleCard()
      case GameState.choosePawn => this.handlePawn()
      case GameState.postTurn => this.handleTurn()
      case GameState.finished => this.handleFinished()
      case GameState.end => this.handleEnd()
    }
  }


  def handleInit(): Unit = {

  }
  def handleRound(): Unit = {

  }
  def handleCard(): Unit = {

  }
  def handlePawn(): Unit = {

  }
  def handleTurn(): Unit = {

  }
  def handleFinished(): Unit = {

  }
  def handleEnd(): Unit = {

  }
}
