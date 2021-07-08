package model

import scala.collection.immutable.List
import scala.collection.mutable.ArrayBuffer

class Deck {
  var cards: List[Card] = this.init()

  def init(): List[Card] = {
    val cardsBuffer = new ArrayBuffer[Card]()

    for (x <- 0 to 55) {
      val card = new Card(x)
      cardsBuffer.addOne(card)
    }
    cardsBuffer.toList
  }

  def shuffle(): Unit = {
    for(_ <- 0 to 4) {
      cards = scala.util.Random.shuffle(cards)
    }
  }

  def draw(amount : Int): List[Card] = {
    val cardsBuffer = ArrayBuffer.from(cards)
    val drawHand = cardsBuffer.slice(0, amount)
    cardsBuffer.diff(drawHand)
    this.cards = cardsBuffer.diff(drawHand).toList

    drawHand.toList
  }
}
