package controller

import model.GameRules

class Controller {
  def main(args: Array[String]): Unit = {
    val gameRules = new GameRules()
    val cardController = new CardController(gameRules)
    val moveController = new MoveController(gameRules)
    val flowController = new FlowController(gameRules)

    flowController.start()
  }
}
