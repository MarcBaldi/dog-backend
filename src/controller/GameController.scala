package controller

import model.GameData

object GameController extends App {

  val gameData = new GameData()
  // only tui for now
  val inputController = new TInputController(gameData)

  val cardController = new CardController(gameData)
  val moveController = new NotScuffedMoveController(gameData)
  val flowController = new FlowController(gameData)

  this.initControllers()
  this.gameLoop()

  def initControllers(): Unit = {
    cardController.init()
    //flowController.init()
  }

  def gameLoop(): Unit = {
    flowController.start()

    flowController.gameLoop()
  }
}
