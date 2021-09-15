package controller

import com.typesafe.scalalogging.Logger
import javafx.util.Pair
import model.{Card, Deck, GameData}

import scala.collection.mutable

class CardController(val gameData: GameData) {
  var deck = new Deck()
  var playerHands: mutable.Map[Int, mutable.ArrayBuffer[Card]] = mutable.Map()
  val logger: Logger = Logger("TInputController")

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

}
