package model

import com.typesafe.scalalogging.Logger

import scala.collection.mutable

case class NotScuffedField(gameData: GameData) {
  private val graph: mutable.Map[FieldNode, mutable.HashSet[FieldNode]] = new mutable.HashMap()

  val logger: Logger = Logger("Field")

  def init(withPawns: Boolean = true): NotScuffedField = {
    this.initFieldNodes()
    if (withPawns) this.initPawns()
    this
  }

  def initFieldNodes(): Unit = {
    var origin: Option[FieldNode] = None
    var lastField: Option[FieldNode] = None

    for (player <- 0 until  this.gameData.playerCount) {
      var playerSpawn: Option[FieldNode] = None

      for (i <- 0 until this.gameData.armLength) {
        val currentField = FieldNodeFactory.createFieldNode()
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
          graph(lastField.get) += currentField
        }
        lastField = Some(currentField)
      }

      var lastGoal: Option[FieldNode] = None
      for (_ <- 0 until this.gameData.pieceAmount) {
        val start = FieldNodeFactory.createFieldNode()
        start.player = player
        start.fieldType = FieldType.start
        val set = new mutable.HashSet[FieldNode]
        set += playerSpawn.get
        graph.put(start, set)
      }

      for (_ <- 0 until this.gameData.pieceAmount) {
        val goal = FieldNodeFactory.createFieldNode()
        goal.player = player
        goal.fieldType = FieldType.goal
        graph.put(goal, new mutable.HashSet[FieldNode])
        if (lastGoal.isEmpty) {
          graph(playerSpawn.get) += goal
        } else {
          graph(lastGoal.get) += goal
        }
        lastGoal = Some(goal)
      }
    }
    graph(lastField.get) += origin.get
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


  def movePawn(pawn: Pawn, newField: FieldNode): Unit = {
    val oldField = this.getField(pawn)
    oldField.resetPawn()
    newField.currentPawn = pawn
  }

  def sendPawnOnField(pawn: Pawn): Unit = {
    val player = pawn.player
    val spawn = this.getSpawnField(player)
    this.movePawn(pawn, spawn)
  }

  def sendPawnHome(pawn: Pawn): Unit = {
    val player = pawn.player
    val home = this.getFreeStartOf(player)
    this.movePawn(pawn, home)
  }

  def swapPawns(pawn1: Pawn, pawn2: Pawn): Unit = {
    val field1 = this.getField(pawn1)
    val field2 = this.getField(pawn2)
    this.movePawn(pawn1, field2)
    this.movePawn(pawn2, field1)
  }

  // ###### HELPER Operations ###
  def getField(pawn: Pawn): FieldNode = {
    for (field <- graph.keys) {
      if (field.currentPawn.nonEmpty && field.currentPawn.get == pawn) {
        return field
      }
    }
    throw new Exception("Gibt Problem Junge. pawn nicht gefunden")
  }

  def getSpawnField(player: Int): FieldNode = {
    for (field <- graph.keys) {
      if (field.fieldType.equals(FieldType.spawn) && field.player.get == player) {
        return field
      }
    }
    throw new Exception("Gibt Problem Junge. Spawn nicht gefunden")
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
        res += field.currentPawn.get
      }
    }
    res
  }

  // ###### DEBUG Operations ###
  // TODO: disable outside of dev

  def getGraph: mutable.Map[FieldNode, mutable.HashSet[FieldNode]] = {
    this.graph
  }

  def initTestBoard(): Unit = {
    PawnFactory.resetPawns()

    var field = this.getFirstField
    var pawn = PawnFactory.createPawn(0)
    field.currentPawn = pawn

    for (_ <- 0 until 4) {
      field = this.getNextField(field)
    }
    pawn = PawnFactory.createPawn(1)
    field.currentPawn = pawn

    for (_ <- 0 until 2) {
      field = this.getNextField(field)
    }
    pawn = PawnFactory.createPawn(2)
    field.currentPawn = pawn

    for (_ <- 0 until 8) {
      field = this.getNextField(field)
    }
    pawn = PawnFactory.createPawn(3)
    field.currentPawn = pawn

    for (_ <- 0 until 5) {
      field = this.getNextField(field)
    }
    pawn = PawnFactory.createPawn(0)
    field.currentPawn = pawn

    for (_ <- 0 until 6) {
      field = this.getNextField(field)
    }
    pawn = PawnFactory.createPawn(1)
    field.currentPawn = pawn

    // TODO: Maybe add some more, and encapsulate

  }

  def getFirstField: FieldNode = {
    for (field <- graph.keys) {
      if (field.fieldType == FieldType.spawn && field.player.contains(0)) {
        return field
      }
    }
    throw new Exception("no first field found!!")
  }

  def getNextField(field: FieldNode): FieldNode = {
    for (step <- graph(field)) {
      if (step.fieldType == FieldType.field || step.fieldType == FieldType.spawn ) {
        return step
      }
    }
    throw new Exception("no Next field found!!")
  }
}
