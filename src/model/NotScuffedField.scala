package model

import scala.collection.mutable

case class NotScuffedField() {

  val graph: mutable.Map[FieldNode, mutable.HashSet[FieldNode]] = new mutable.HashMap()

  def init(): Unit = {
    // TODO: read this from gamerules
    val playerCount = 4
    this.initFieldNodes(playerCount)
    this.initPawns(playerCount)

  }


  def initFieldNodes(playerCount: Int): Unit = {
    // TODO: read this from gamerules
    val normalFieldCount = 15

    // TODO: null to option
    var origin:FieldNode = null
    var lastField:FieldNode = null

    for (_ <- 0 until  playerCount) {
      var playerSpawn:FieldNode = null

      for (i <- 0 until normalFieldCount) {
        val currentField = FieldNodeFactory.createFieldNode("field")
        graph.put(currentField, new mutable.HashSet[FieldNode])

        if (i == 0) {
          playerSpawn = currentField
        }
        if (lastField == null) {
          origin = currentField
          lastField = currentField
        } else {
          graph(lastField).addOne(currentField)
          lastField = currentField
        }
      }

      var lastGoal:FieldNode = null
      for (_ <- 0 until 4) {
        val start = FieldNodeFactory.createFieldNode("start")
        val set = new mutable.HashSet[FieldNode]
        set.addOne(playerSpawn)
        graph.put(start, set)
      }
      for (_ <- 0 until 4) {
        val goal = FieldNodeFactory.createFieldNode("goal")
        graph.put(goal, new mutable.HashSet[FieldNode])
        if (lastGoal == null) {
          graph(lastField).addOne(goal)
        } else {
          graph(lastGoal).addOne(goal)
        }
        lastGoal = goal
      }
    }
    graph(lastField).addOne(origin)
  }

  def initPawns(playerCount: Int): Unit = {

    PawnFactory.resetPawns()

    for (playerID <- 0 until 4) {
      val player = new Player(playerID)
      for (_ <- 0 until 4) {

      }
    }
  }
    /*
      def reCalcView(): Unit = {
        var course = new ArrayBuffer[Position]()
        var entryPoints = new ArrayBuffer[Position]()
        var playerStartPositions: mutable.Map[Int, ArrayBuffer[Position]] = collection.mutable.Map(0 -> initArrayBuffer())
        var playerEndPositions: mutable.Map[Int, ArrayBuffer[Position]] = collection.mutable.Map(0 -> initArrayBuffer())

        for ((pawn, position) <- playerPositions) {
          if (position) {

          }
        }


      }*/
}
