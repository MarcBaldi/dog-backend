package controller

import com.typesafe.scalalogging.Logger

import scala.collection.mutable
import model.{Card, FieldNode, GameData, NotScuffedField, Pawn}

class NotScuffedMoveController(val gameData: GameData) {
  private val field = NotScuffedField(gameData)
  private val simpleCards = List(2,3,5,6,8,9,10,12)

  val logger: Logger = Logger("MoveController")

  def init(withPawns: Boolean = true): Unit = {
    field.init(withPawns)
  }

  // ###### MOVE Operations ###
  def move(pawn: Pawn, newField: FieldNode): Unit = {
    if (newField.currentPawn.nonEmpty) {
      field.sendPawnHome(newField.currentPawn.get)
    }
    field.movePawn(pawn, newField)
  }

  // Convenient method
  def move(pawn: Pawn, newField: Int): Unit = {
    val newFieldField = field.getGraph.keys.find(f => f.id == newField)
    if (newFieldField.nonEmpty) {
      this.move(pawn, newFieldField.get)
    } else {
      logger.error("Pawn " + pawn + " could not move to FieldId " + newField + ". Could not find field.")
      throw new Exception("Could not move Pawn")
    }
  }

  // BOOBA
  def move11(pawn1: Pawn, pawn2: Pawn): Unit = {
    field.swapPawns(pawn1, pawn2)
  }

  // ASS, KING
  def spawnPawn(pawn: Pawn): Unit = {
    field.sendPawnOnField(pawn)
  }


  // TODO: player may not walk into goals of the others
  // ###### Calculations Operations ###
  def calcPossibleTargets(node: FieldNode, moveCount: Int): mutable.HashSet[FieldNode] = {
    val list: mutable.HashSet[FieldNode] = mutable.HashSet()
    calcPossibleTargetsR(list, node, moveCount, node.currentPawn.get.player)
  }

  def calcPossibleTargetsR(targets: mutable.HashSet[FieldNode], start: FieldNode, moveCount: Int, player: Int): mutable.HashSet[FieldNode] = {
    if (moveCount == 0) {return targets}
    for (step <- field.getGraph(start)) {
      if (step.player.isEmpty || step.player.contains(player)) {
        if (moveCount == 1) {
          targets += step
        }
        // add all, because in loop. Scala gods forgive me
        targets.addAll(this.calcPossibleTargetsR(targets, step, moveCount - 1, player))
      }
    }
    targets
  }

  def calcPossibleTargetsReverse(node: FieldNode, moveCount: Int): mutable.HashSet[FieldNode] = {
    val list: mutable.HashSet[FieldNode] = mutable.HashSet()
    calcPossibleTargetsReverseR(list, node, moveCount)
  }

  def calcPossibleTargetsReverseR(targets: mutable.HashSet[FieldNode], start: FieldNode, moveCount: Int): mutable.HashSet[FieldNode] = {
    if (moveCount == 0) {return targets += start}

    // find precedents
    val precedents: mutable.HashSet[FieldNode] = mutable.HashSet()
    for (step <- field.getGraph) {
       if (step._2.contains(start)) {
         precedents += step._1
       }
    }
      for (step <- precedents) {
      // add all, because in loop
      targets.addAll(this.calcPossibleTargetsReverseR(targets, step, moveCount - 1))
    }
    targets
  }




  // ###### CHECKS Operations ###
  def isPlayerFinished(player: Int): Boolean = {
    for (field <- this.field.getGraph.keys) {
      if (field.fieldType == model.FieldType.goal && field.player.contains(player) && field.currentPawn.isEmpty) {
        return false
      }
    }
    true
  }

  def isSimpleCard(card: Card): Boolean = {
    simpleCards.contains(card.getValue)
  }

  def hasCardChoicesNow(pawn: Pawn, card: Card): Boolean = {
    if (isSimpleCard(card)) {
      calcPossibleTargets(field.getField(pawn), card.getValue).size > 1
    } else {
      // TODO: probably..
      true
    }
  }

  def isCardPlayable(pawn: Pawn, card: Card): Boolean = {
    if (isSimpleCard(card)) {
      calcPossibleTargets(field.getField(pawn), card.getValue).nonEmpty
    } else {
      // TODO: probably..
      true
    }
  }

  // ###### DEBUG Operations ###
  // TODO: disable outside of dev

  def getField: NotScuffedField = {
    field
  }

  def getRandomPawn: Pawn = {
    for (x <- field.getGraph)
      if (x._1.currentPawn.nonEmpty) {
        return x._1.currentPawn.get
      }
    throw new Exception("Danger. kein pawn gefunden")
  }

  def printField(): Unit = {
    for (x <- field.getGraph)
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
