package controller

import model.{Card, Deck, GameRules, Position}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class CardController(val gameRules: GameRules) {
  var deck = new Deck()
  var playerHands: mutable.Map[Int, ArrayBuffer[Card]] = collection.mutable.Map(0 -> new ArrayBuffer[Position]())

  def init(): Unit = {
    deck.shuffle()

    for (i <- 0 to gameRules.playerCount) {

      playerHands += (i -> deck.draw(6))

    }

  }

}
