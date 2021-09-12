package controller

import scala.collection.mutable
import model.{Card, FieldNode, GameRules, NotScuffedField, Pawn}

class NotScuffedMoveController(val gameRules: GameRules, withPawns: Boolean = true) {
  private val field = NotScuffedField(gameRules).init(withPawns)
  private val simpleCards = List(2,3,5,6,8,9,10,12)

  // ###### MOVE Operations ###
  def move(pawn: Pawn, card: Card): Unit = {
    if (isSimpleCard(card)) {
      moveSimple(pawn, card)
    } else {
      // TODO: Handling for special cards
      moveComplex(pawn, card)
    }
  }

  def moveSimple(pawn: Pawn, card: Card): Unit = {
    val possibleFields = calcPossibleTargets(field.getField(pawn), card.getValue)
    field.movePawn(pawn, possibleFields.head)
    println("moved:" + pawn)
    println("to: " + possibleFields.head)
  }

  def moveComplex(pawn: Pawn, card: Card): Unit = {
    if (card.getValue == 1) {
      this.move1(pawn)
    } else if (card.getValue == 4) {
      this.move4(pawn)

    } else if (card.getValue == 7) {
      this.move7(pawn)

    } else if (card.getValue == 11) {
      this.move11(pawn)

    } else if (card.getValue == 13) {
      this.move13(pawn)

    } else if (card.getValue == 14) {
      this.move14(pawn)

    }
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
      // add all, because in loop
      targets.addAll(this.calcPossibleTargetsR(targets, step, moveCount - 1))
    }
    targets
  }

  def calcPossibleTargetsReverse(node: FieldNode, moveCount: Int): mutable.HashSet[FieldNode] = {
    val list: mutable.HashSet[FieldNode] = mutable.HashSet()
    calcPossibleTargetsReverseR(list, node, moveCount)
  }

  def calcPossibleTargetsReverseR(targets: mutable.HashSet[FieldNode], start: FieldNode, moveCount: Int): mutable.HashSet[FieldNode] = {
    if (moveCount == 0) {return targets}

    // find precedents
    val precedents: mutable.HashSet[FieldNode] = mutable.HashSet()
    for (step <- field.graph) {
       if (step._2.contains(start)) {
         precedents.addOne(step._1)
       }
    }
    for (step <- precedents) {
      // add all, because in loop
      targets.addAll(this.calcPossibleTargetsReverseR(targets, step, moveCount - 1))
    }
    targets
  }

  def move1(pawn: Pawn): Unit = {
    // ASS
    // TODO: input 1,11
    val input = 1
    val possibleFields = calcPossibleTargets(field.getField(pawn), input)
    field.movePawn(pawn, possibleFields.head)
  }

  def move4(pawn: Pawn): Unit = {
    // TODO: input 4,-4
    val input = -4
    val possibleFields =
      if (input == 4) {
        calcPossibleTargets(field.getField(pawn), input)

      } else {
        calcPossibleTargetsReverse(field.getField(pawn), -input)

      }
    field.movePawn(pawn, possibleFields.head)
  }

  def move7(pawn: Pawn): Unit = {
    // TODO: input single pawns
    val input = null // ??
    field.movePawn(pawn, input)
  }

  def move11(pawn: Pawn): Unit = {
    // BOOBA
    // TODO: input second pawn
    val input = getRandomPawn
    field.swapPawns(pawn, input)
  }

  def move13(pawn: Pawn): Unit = {
    // KING
    // TODO: input 13, spawn(0?)
    val input = 13
  }

  def move14(pawn: Pawn): Unit = {
    // JOKER
    // TODO: input 1,2,3,4,5,6,7,8,9,10,11,12,13
    val input = 1
    this.move(pawn, new Card(input))
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

  def demo(): Unit = {
    val pawn = getRandomPawn
    println(field.getField(pawn))
    field.sendPawnHome(pawn)
    println(field.getField(pawn))

    field.sendPawnOnField(pawn)
    println(field.getField(pawn))

  }

}
