package controller

import model.GameRules

class Controller {
  def main(args: Array[String]): Unit = {
    val gameRules = new GameRules()
    val cardController = new CardController(gameRules)
    val moveController = new HardScuffedMoveController(gameRules)
    val flowController = new FlowController(gameRules)

    cardController.init()
    flowController.start()

  }
}
