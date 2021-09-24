package controller

import com.typesafe.scalalogging.Logger
import model.{GameData, PawnFactory}

import scala.collection.mutable.ArrayBuffer
import scala.io.StdIn.readInt

class TInputController(override val gameData: GameData) extends InputController(gameData) {

  val logger: Logger = Logger("TInputController")

  override def cardInput: Option[model.Card] = {
    print("Enter the Number on your Card to play it: ")
    val input = intInput
    if (input.isEmpty) None else Some(model.Card(input.get))
  }

  override def pawnInput: Option[model.Pawn] = {
    print("Enter the Number next to your Pawn to move it: ")
    val input = intInput
    if (input.isEmpty) return None
    val allPawns = PawnFactory.getAllPawns
    if (!allPawns.exists(p => {p.id == input.get && p.player == gameData.currentPlayer})) {
      logger.error("Pawn not found!")
      return None
    }
    if (input.isEmpty) None else Some(allPawns.filter(p=>{p.id==input.get && p.player == gameData.currentPlayer}).head)
  }

  override def fieldInput: Option[Int] = {
    print("Enter the Number on the Field to choose it: ")
    intInput
  }

  override def outputPossibleFields(fields: ArrayBuffer[model.FieldNode]): Unit = {
    //this.outputFieldV1(field)
    // TODO: implement this!
  }
  
  override def outputField(field: model.NotScuffedField): Unit = {
    //this.outputFieldV1(field)
    this.outputFieldV2(field)
  }

  override def outputCards(hand: ArrayBuffer[model.Card]): Unit = {
    println(hand.mkString("Cards: ", ", ", ""))
  }

  override def announcePlayerTurn(): Unit = {
    println("Now it´s your turn: " + gameData.playerColors(gameData.currentPlayer))
  }

  override def announceJokerMessage(): Unit = {
    println("Which card should the Joker be? Enter the ID")
    for (c <- 1 until 14) {
      val card = model.Card(c)
      print(card.getValue + ": ")
      println(card.getDescription)
    }
  }

  override def announceFinishedMessage(player: Int): Unit = {
    println("Player " + gameData.playerColors(player) + " has finished!")
    println("Allied pawns have been made available.")
  }

  override def announceEndMessage(player: Int): Unit = {
    println("Player " + gameData.playerColors(player) + " and " + gameData.playerColors(gameData.getAllyPlayer(player)) + " have won!!!")
  }


  // ##### Private functions

  private def intInput: Option[Int] = {
    val input: Int = try {
      readInt()
    }
    catch {
      case _: java.io.EOFException =>
        print("Input cancelled.")
        return None
      case _: java.lang.NumberFormatException =>
        print("I did not understand. Please try again, or press Enter to abort.")
        return intInput
      case ex: Throwable =>
        logger.error("Problem with Input")
        throw ex
    }
    Some(input)
  }


  private def outputFieldV1(field: model.NotScuffedField): Unit = {
    //val graph = field.getGraph
    println("printing graph as Pawns:")
    val pawns = field.getAllPawns
    println(field.getAllPawns.size)

    for (pawn <- pawns) {
      val cField = field.getField(pawn)
      print(cField.fieldType + " " + cField.id + "\t")
      println(gameData.playerColors(pawn.player) + " \t"+ pawn.id)
    }



  }
  private def outputFieldV2(field: model.NotScuffedField): Unit = {
    println("printing graph as Field:")

    val spawn = field.getSpawnField(0)
    var currentField = spawn
    field.getNextField(spawn)
    //println("[" + spawn.id +"] ["+ field.getNextField(spawn).id + "] [" + spawn.id+ "]")


    for (pla <- 0 until 4 ) {
      println("Spawn " + pla)
      for (_ <- 0 until 15) {
        if (currentField.currentPawn.isEmpty) {
          this.outputCell(currentField)
        } else {
          this.outputPawn(currentField.currentPawn.get)
        }
        currentField = field.getNextField(currentField)
      }
      println("")
    }

    println("Start " + gameData.playerColors(gameData.currentPlayer))
    for (f <- field.getGraph.keys) {
      if (f.fieldType == model.FieldType.start && f.player.get == gameData.currentPlayer) {
        if (f.currentPawn.isEmpty) {
          this.outputCell(f)
        } else {
          this.outputPawn(f.currentPawn.get)
        }
      }
    }
    println("")

    println("Goal " + gameData.playerColors(gameData.currentPlayer))
    for (f <- field.getGraph.keys) {
      if (f.fieldType == model.FieldType.goal && f.player.get == gameData.currentPlayer) {
        if (f.currentPawn.isEmpty) {
          this.outputCell(f)
        } else {
          this.outputPawn(f.currentPawn.get)
        }
      }
    }
    println("")

  }

  private def outputCell(field: model.FieldNode): Unit = {
    if (("" + field.id).length <= 2) {
      print("[" + field.id + " ] ")
    } else {
      print("[" + field.id + "] ")
    }
  }

  private def outputPawn(pawn: model.Pawn): Unit = {
    print("" + gameData.playerColors(pawn.player).substring(0,3) + " " + pawn.id + " ")
  }
}
