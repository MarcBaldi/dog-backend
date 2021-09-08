package controller

import model.{Card, GameRules, HardScuffedField4, Pawn}

class HardScuffedMoveController(val gameRules: GameRules) {

  var field = new HardScuffedField4()
  field.init()

  // ###### SIMPLE Operations ###
  def sendPawnOnField(pawn: Pawn): Unit = {
    field.playerPositions(pawn) = field.startPositions(pawn.player)
  }

  def sendPawnHome(pawn: Pawn): Unit = {
    val positionId = (pawn.player + 1) * 100
    val range = positionId until positionId + 4

    val res = range.toSet -- field.playerPositions.values
    field.playerPositions(pawn) = res.head
  }

  def moveSimple(pawn: Pawn, card: Card): Unit = {
    val target = (field.playerPositions(pawn) + card.getValue) % 64
    field.playerPositions(pawn) = target
  }

  def move(pawn: Pawn, card: Card): Unit = {
    if (isSimpleMove(pawn, card)) {
      moveSimple(pawn, card)
    } else {
      // TODO:

    }
  }

  def isSimpleMove(pawn: Pawn, card: Card): Boolean = {
     isSimpleCard(card) && !hasCardChoicesNow(pawn, card)
  }

  def isSimpleCard(card: Card): Boolean = {
    // TODO:
    val simpleCards = List(2,3,5,6,8,9,10)
    if (simpleCards.contains(card.getValue)) {
      return true
    }
    false
  }

  def hasCardChoicesNow(pawn: Pawn, card: Card): Boolean = {
    // TODO:
    false
  }

  def calcTargets(pawn: Pawn, card: Card): Unit = {
    // TODO:
  }

  def calcTargets(pawn: Pawn, cards: List[Card]): Unit = {
    // TODO:
  }

  def isPawnMovable(pawn: Pawn): Boolean = {
    // TODO:
    true
  }

  def isCardPlayable(pawn: Pawn, card: Card): Boolean = {
    // TODO:
    true
  }



}
