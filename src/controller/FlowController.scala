package controller

import com.typesafe.scalalogging.Logger
import model.{Card, FieldNode, GameData, GameState, Pawn}
import sun.reflect.generics.reflectiveObjects.NotImplementedException

class FlowController(val gameData: GameData, inputController: InputController, cardController: CardController, moveController: NotScuffedMoveController) {

  var chosenCard: Option[Card] = None
  var chosenPawn: Option[Pawn] = None
  var chosenField: Option[FieldNode] = None
  val logger: Logger = Logger("FlowController")

  def init(): Unit = {

  }

  def start(): Unit = {
    println("""
    888888ba                     .88888.
    88    `8b                   d8'   `88
    88     88 .d8888b. .d8888b. 88        .d8888b. 88d8b.d8b. .d8888b.
    88     88 88'  `88 88'  `88 88   YP88 88'  `88 88'`88'`88 88ooood8
    88    .8P 88.  .88 88.  .88 Y8.   .88 88.  .88 88  88  88 88.  ...
    8888888P  `88888P' `8888P88  `88888'  `88888P8 dP  dP  dP `88888P'
                            .88
                        d8888P
    """)
    println("m"*19*5)
  }

  def gameLoop(): Unit = {
    while (gameData.gameState != GameState.end) {
      this.gameTick()
    }
  }

  def gameTick(): Unit = {
    logger.info("Launching tick: " + gameData.gameState)
    gameData.gameState match  {
      case GameState.init => this.handleInit()
      case GameState.preRound => this.handleRound()
      case GameState.chooseCard => this.handleCard()
      case GameState.chooseCardJ => this.handleCardJ() //joker
      case GameState.choosePawn => this.handlePawn()
      case GameState.choosePawn2 => this.handlePawn2() //11
      case GameState.choosePawns => this.handlePawns() //7
      case GameState.postTurn => this.handleTurn()
      case GameState.finished => this.handleFinished()
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
    inputController.announcePlayerTurn()
    val hand = cardController.playerHands(this.gameData.currentPlayer)
    inputController.outputCards(hand)
    this.chosenCard = inputController.cardInput

    if (chosenCard.contains(Card(7))) {
      this.gameData.gameState = GameState.choosePawns
    } else if (chosenCard.contains(Card(11))) {
      this.gameData.gameState = GameState.choosePawn2
    } else if (chosenCard.contains(Card(0))) {
      this.gameData.gameState = GameState.chooseCardJ
    } else {
      this.gameData.gameState = GameState.choosePawn
    }
  }

  // Joker
  def handleCardJ(): Unit = {
    inputController.announceJokerMessage()
    val chosenCardJ = inputController.cardInput
    if (chosenCard.isEmpty) {
      throw new Exception("No Card chosen")
    }
    if (chosenCard.contains(Card(0))) {
      throw new Exception("Cannot choose Joker")
    }

    this.chosenCard = this.cardController.transformJokerCard(gameData.currentPlayer, chosenCardJ.get)
    this.gameData.gameState = GameState.choosePawn
  }

  def handlePawn(): Unit = {
    inputController.outputField(moveController.getField)
    val pawn = inputController.pawnInput
    moveController.move(pawn.get, chosenCard.get)

    logger.info("moved pawn "+pawn.get+ ", size is now: "+ moveController.getField.getAllPawns.size)
    this.gameData.gameState = GameState.postTurn
  }

  // 11
  def handlePawn2(): Unit = {
    val pawnSelf = inputController.pawnInput
    val pawnOther = inputController.pawnInput
    moveController.move11(pawnSelf.get, pawnOther.get)

    logger.info("moved pawns "+pawnSelf.get + " + "+pawnOther.get)
    this.gameData.gameState = GameState.postTurn
  }

  // 7
  def handlePawns(): Unit = {
    // TODO:
    throw new NotImplementedException
  }

  def handleTurn(): Unit = {
    if (moveController.isPlayerFinished(this.gameData.currentPlayer)) {
      this.gameData.gameState = GameState.finished
    } else if (cardController.arePlayerHandsEmpty()) {
      gameData.nextPlayerTurn()
      this.gameData.gameState = GameState.preRound
    } else {
      gameData.nextPlayerTurn()
      this.gameData.gameState = GameState.chooseCard
    }
  }

  def handleFinished(): Unit = {
    inputController.announceFinishedMessage(this.gameData.currentPlayer)
    if (moveController.isPlayerFinished(this.gameData.getAllyPlayer(this.gameData.currentPlayer))) {
      inputController.announceEndMessage(this.gameData.currentPlayer)
      this.gameData.gameState = GameState.end
    }
    // TODO: make Pawns of ally movable for currentPlayer

    gameData.nextPlayerTurn()
    if (cardController.arePlayerHandsEmpty()) {
      this.gameData.gameState = GameState.preRound
    } else {
      this.gameData.gameState = GameState.chooseCard
    }
  }

  // ####### Helpers
  def defineCardCount(currentRound: Int): Int = {
    val result = 6 - (currentRound % 5)
    logger.info("calcCards: " + result)
    result
  }
}
