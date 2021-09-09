package controller

import scala.collection.mutable
import model.{Card, FieldNode, GameRules, NotScuffedField, Pawn}

class NotScuffedMoveController(val gameRules: GameRules) {
  private val field = NotScuffedField(gameRules).init()
  private val simpleCards = List(2,3,5,6,8,9,10,12)

  // ###### MOVE Operations ###
  def move(pawn: Pawn, card: Card): Unit = {
    if (isSimpleCard(card)) {
      moveSimple(pawn, card)
    } else {
      // TODO: Handling for special cards
    }
  }

  def moveSimple(pawn: Pawn, card: Card): Unit = {
    val possibleFields = calcPossibleTargets(field.getField(pawn), card.getValue)
    this.movePawn(pawn, possibleFields.head)
  }




  def movePawn(pawn: Pawn, target: FieldNode): Unit = {
    field.movePawn(pawn, target)
    println("moved:" + pawn)
    println("to: " + target)
  }

  def sendPawnOnField(pawn: Pawn): Unit = {
    //field.playerPositions(pawn) = field.startPositions(pawn.player)
  }

  def sendPawnHome(pawn: Pawn): Unit = {
    //val positionId = (pawn.player + 1) * 100
    //val range = positionId until positionId + 4

    //val res = range.toSet -- field.playerPositions.values
    //field.playerPositions(pawn) = res.head
  }



  def calcPossibleTargets(node: FieldNode, moveCount: Int): mutable.HashSet[FieldNode] = {
    val list: mutable.HashSet[FieldNode] = mutable.HashSet()
    calcPossibleTargetsR(list, node, moveCount)
  }

  def calcPossibleTargetsR(targets: mutable.HashSet[FieldNode], start: FieldNode, moveCount: Int): mutable.HashSet[FieldNode] = {
    if (moveCount == 0) {return targets}
    for (step <- field.graph(start)) {
      if (moveCount == 1) {
        targets.addOne(step)
      }
      targets.addAll(this.calcPossibleTargetsR(targets, step, moveCount - 1))
    }
    targets
  }

  def calcTargets(pawn: Pawn, card: Card): Unit = {
    // TODO:
  }

  def calcTargets(pawn: Pawn, cards: List[Card]): Unit = {
    // TODO:
  }

  // ###### CHECKS Operations ###
  def isSimpleMove(pawn: Pawn, card: Card): Boolean = {
     isSimpleCard(card) && !hasCardChoicesNow(pawn, card)
  }

  def isSimpleCard(card: Card): Boolean = {
    simpleCards.contains(card.getValue)
  }

  def hasCardChoicesNow(pawn: Pawn, card: Card): Boolean = {
    // TODO:
    false
  }



  def isPawnMovable(pawn: Pawn): Boolean = {
    // TODO:
    true
  }

  def isCardPlayable(pawn: Pawn, card: Card): Boolean = {
    // TODO:
    true
  }


  // ###### DEBUG Operations ###
  // TODO: disable outside of dev

  def getField: NotScuffedField = {
    field
  }

  def getRandomPawn: Pawn = {
    for (x <- field.graph)
      if (x._1.currentPawn.nonEmpty) {
        return x._1.currentPawn.get
      }
    throw new Exception("Danger. kein pawn gefunden")
  }

  def printField(): Unit = {
    for (x <- field.graph)
      println(x)
  }

}
