package controller

import model.{Card, Deck, GameRules}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class CardController(val gameRules: GameRules) {
  var deck = new Deck()
  var playerHands: mutable.Map[Int, ArrayBuffer[Card]] = collection.mutable.Map(0 -> new ArrayBuffer[Card]())

  def init(): Unit = {
    drawHands(6)
  }

  def drawHands(cardCount: Int): Unit = {
    deck.shuffle()
    for (i <- 0 to gameRules.playerCount) {
      playerHands += (i -> deck.draw(cardCount))
    }
  }

}
