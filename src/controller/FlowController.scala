package controller

import com.typesafe.scalalogging.Logger
import model.{GameData, GameState}

class FlowController(val gameData: GameData, inputController: InputController, cardController: CardController, moveController: NotScuffedMoveController) {

  var currentCard: Option[model.Card] = None

  val logger: Logger = Logger("TInputController")
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
    logger.info("Launching tick: " + gameData.gameState)
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
  // TODO: error handling

  def handleInit(): Unit = {
    this.gameData.gameState = GameState.preRound
  }
  def handleRound(): Unit = {
    val cardCount = this.defineCardCount(this.gameData.currentRound)
    cardController.drawHands(cardCount)
    // TODO: allies swap a card

    this.gameData.gameState = GameState.chooseCard
  }
  def handleCard(): Unit = {
    this.printHand()
    this.currentCard = inputController.cardInput

    this.gameData.gameState = GameState.choosePawn
  }
  def handlePawn(): Unit = {
    inputController.outputField(moveController.getField)
    val pawn = inputController.pawnInput
    moveController.move(pawn.get,currentCard.get)
    logger.info("moved pawn "+pawn.get+ ", size is now: "+ moveController.getField.getAllPawns.size)

    this.gameData.gameState = GameState.postTurn
  }
  def handleTurn(): Unit = {
    if (moveController.isPlayerFinished(this.gameData.currentPlayer)) {
      this.gameData.gameState = GameState.finished
    } else {
      this.gameData.gameState = GameState.preRound
    }
  }
  def handleFinished(): Unit = {
    // TODO:
  }
  def handleEnd(): Unit = {
    // TODO:
  }

  // Helpers
  def defineCardCount(currentRound: Int): Int = {
    logger.info("calcCards: " + (6 - (currentRound % 5)))
    6 - (currentRound % 5)
  }
  def printHand(): Unit = {
    val hand = cardController.playerHands(this.gameData.currentPlayer)
    println(hand)
  }
}
