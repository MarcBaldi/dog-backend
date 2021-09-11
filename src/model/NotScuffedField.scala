package model

import com.sun.org.apache.xpath.internal.operations.And

import scala.collection.mutable

case class NotScuffedField(gameRules: GameRules) {

  val graph: mutable.Map[FieldNode, mutable.HashSet[FieldNode]] = new mutable.HashMap()

  def init(withPawns: Boolean = true): NotScuffedField = {
    this.initFieldNodes()
    if (withPawns) this.initPawns()
    this
  }

  def initFieldNodes(): Unit = {
    var origin: Option[FieldNode] = None
    var lastField: Option[FieldNode] = None

    for (player <- 0 until  this.gameRules.playerCount) {
      var playerSpawn: Option[FieldNode] = None

      for (i <- 0 until this.gameRules.armLength) {
        val currentField = FieldNodeFactory.createFieldNode("field")
        currentField.fieldType = FieldType.field
        graph.put(currentField, new mutable.HashSet[FieldNode])

        if (i == 0) {
          currentField.player = player
          currentField.fieldType = FieldType.spawn
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
        start.player = player
        start.fieldType = FieldType.start
        val set = new mutable.HashSet[FieldNode]
        set.addOne(playerSpawn.get)
        graph.put(start, set)

      }
      for (_ <- 0 until this.gameRules.pieceAmount) {
        val goal = FieldNodeFactory.createFieldNode("goal")
        goal.player = player
        goal.fieldType = FieldType.goal
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

  // Create pawns in start fields
  def initPawns(): Unit = {
    PawnFactory.resetPawns()
    for (field <- graph.keys) {
      if (field.fieldType.equals(FieldType.spawn)) {
        val pawn = PawnFactory.createPawn(field.player.get)
        field.currentPawn = pawn
      }
    }
  }


  def getField: mutable.Map[FieldNode, mutable.HashSet[FieldNode]] = {
    this.graph
  }

  def getField(pawn: Pawn): FieldNode = {
    for (field <- graph.keys) {
      if (field.currentPawn.nonEmpty && field.currentPawn.get == pawn) {
        return field
      }
    }
    throw new Exception("Gibt Problem Junge. pawn nicht gefunden")
  }

  def movePawn(pawn: Pawn, newField: FieldNode): Unit = {
    val oldField = this.getField(pawn)
    oldField.resetPawn()
    newField.currentPawn = pawn
  }

  def sendPawnOnField(pawn: Pawn): Unit = {
    val player = pawn.player
    val spawn = this.getSpawnField(player)
    movePawn(pawn, spawn)
  }

  def getSpawnField(player: Int): FieldNode = {
    for (field <- graph.keys) {
      if (field.fieldType.equals(FieldType.spawn) && field.player.get == player) {
        return field
      }
    }
    throw new Exception("Gibt Problem Junge. Spawn nicht gefunden")
  }

  def sendPawnHome(pawn: Pawn): Unit = {
    val player = pawn.player
    val home = this.getFreeStartOf(player)
    movePawn(pawn, home)
  }

  def getFreeStartOf(player: Int): FieldNode = {
    for (field <- graph.keys) {
      if (field.fieldType.equals(FieldType.start) && field.player.get == player && field.currentPawn.isEmpty) {
        return field
      }
    }
    throw new Exception("Gibt Problem Junge. freeHome nicht gefunden")
  }


  def getAllPawns: mutable.HashSet[Pawn] = {
    val res = new mutable.HashSet[Pawn]()
    for (field <- graph.keys) {
      if (field.currentPawn.nonEmpty) {
        res.addOne(field.currentPawn.get)
      }
    }
    res
  }

  // ###### DEBUG Operations ###
  // TODO: disable outside of dev

  def initTestBoard(): Unit = {
    PawnFactory.resetPawns()
    var counter = 0
    for (field <- graph.keys) {
      if (field.currentPawn.isEmpty) {
        val pawn = PawnFactory.createPawn((Math.random()*gameRules.playerCount).toInt)
        field.currentPawn = pawn
        counter += 1
        if (counter >= 10) {
          return
        }
      }
    }
  }
}
