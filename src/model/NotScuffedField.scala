package model

import scala.collection.mutable

case class NotScuffedField(gameRules: GameRules) {

  val graph: mutable.Map[FieldNode, mutable.HashSet[FieldNode]] = new mutable.HashMap()

  def init(): NotScuffedField = {
    this.initFieldNodes()
    this
  }

  def initFieldNodes(): Unit = {
    var origin: Option[FieldNode] = None
    var lastField: Option[FieldNode] = None

    for (player <- 0 until  this.gameRules.playerCount) {
      var playerSpawn: Option[FieldNode] = None
      PawnFactory.resetPawns()

      for (i <- 0 until this.gameRules.armLength) {
        val currentField = FieldNodeFactory.createFieldNode("field")
        graph.put(currentField, new mutable.HashSet[FieldNode])

        if (i == 0) {
          playerSpawn = Some(currentField)
        }
        if (lastField.isEmpty) {
          origin = Some(currentField)
        } else {
          graph(lastField.get).addOne(currentField)
        }
        lastField = Some(currentField)
      }

      var lastGoal: Option[FieldNode] = None
      for (_ <- 0 until this.gameRules.pieceAmount) {
        val start = FieldNodeFactory.createFieldNode("start")
        val set = new mutable.HashSet[FieldNode]
        set.addOne(playerSpawn.get)
        graph.put(start, set)

        // Create pawns in start fields
        val pawn = PawnFactory.createPawn(player)
        start.currentPawn = pawn
      }
      for (_ <- 0 until this.gameRules.pieceAmount) {
        val goal = FieldNodeFactory.createFieldNode("goal")
        graph.put(goal, new mutable.HashSet[FieldNode])
        if (lastGoal.isEmpty) {
          graph(lastField.get).addOne(goal)
        } else {
          graph(lastGoal.get).addOne(goal)
        }
        lastGoal = Some(goal)
      }
    }
    graph(lastField.get).addOne(origin.get)
  }


  def getField: mutable.Map[FieldNode, mutable.HashSet[FieldNode]] = {
    this.graph
  }

  def getField(pawn: Pawn): FieldNode = {
    for (field <- graph) {
      if (field._1.currentPawn.nonEmpty && field._1.currentPawn.get == pawn) {
        return field._1
      }
    }
    throw new Exception("Gibt Problem Junge. pawn nicht gefunden")
  }

  def movePawn(pawn: Pawn, newField: FieldNode): Unit = {
    val oldField = this.getField(pawn)
    oldField.resetPawn()
    newField.currentPawn = pawn
  }

  def getAllPawns: mutable.HashSet[Pawn] = {
    val res = new mutable.HashSet[Pawn]()
    for (field <- graph) {
      if (field._1.currentPawn.nonEmpty) {
        res.addOne(field._1.currentPawn.get)
      }
    }
    res
  }

  // ###### DEBUG Operations ###
  // TODO: disable outside of dev

  def initTestBoard(): Unit = {
    // TODO: put some example pawns
    var counter = 0
    for (field <- graph) {
      if (field._1.currentPawn.isEmpty) {
        val pawn = PawnFactory.createPawn(0)
        field._1.currentPawn = pawn
        counter += 1
        if (counter >= 10) {
          return
        }
      }
    }
  }
}