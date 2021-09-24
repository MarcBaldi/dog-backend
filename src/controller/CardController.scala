package controller

import com.typesafe.scalalogging.Logger
import javafx.util.Pair
import model.{Card, Deck, GameData, Player}

import scala.collection.mutable

class CardController(val gameData: GameData) {
  var deck = new Deck()
  var playerHands: mutable.Map[Int, mutable.ArrayBuffer[Card]] = mutable.Map()
  private val simpleCards = List(2,3,5,6,8,9,10,12)
  val logger: Logger = Logger("CardController")

  def init(): Unit = {
    playerHands.clear()
    for (player <- 0 until gameData.playerCount) {
      playerHands += ((player, new mutable.ArrayBuffer[Card]()))
      logger.info(s"added player $player to playerHands")
    }
  }

  def drawHands(cardCount: Int): Unit = {
    deck.shuffle()
    for (i <- 0 to gameData.playerCount) {
      playerHands += (i -> deck.draw(cardCount))
    }
  }

  def arePlayerHandsEmpty(): Boolean = {
    this.playerHands.values.forall(it => it.isEmpty)
  }

  def transformJokerCard(player: Int, targetCard: Card): Option[Card] = {
    if (!this.playerHands(player).contains(Card(0))) {
      throw new Exception("No Joker found")
    }
    this.playerHands(player).-=(Card(0))
    this.playerHands(player).+=(targetCard)
    Some(targetCard)
  }

  def isSimpleCard(card: Card): Boolean = {
    simpleCards.contains(card.getValue)
  }
}
