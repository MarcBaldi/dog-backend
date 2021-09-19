package controller

import com.typesafe.scalalogging.Logger
import model.{GameData, PawnFactory}

import scala.io.StdIn.readInt

class TInputController(override val gameData: GameData) extends InputController(gameData) {

  val logger: Logger = Logger("TInputController")

  override def cardInput: Option[model.Card] = {
    print("Enter the Number on your Card to play it: ")
    val input = intInput
    if (input.isEmpty) None else Some(model.Card(input.get))
  }

  override def pawnInput: Option[model.Pawn] = {
    print("Enter the Number on your Pawn to move it: ")
    val input = intInput
    if (input.isEmpty) return None
    val allPawns = PawnFactory.getAllPawns
    if (!allPawns.exists(p => {p.id == input.get})) {
      logger.error("Pawn not found!")
      return None
    }
    if (input.isEmpty) None else Some(allPawns.filter(p=>{p.id==input.get}).head)
  }


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



  override def outputField(field: model.NotScuffedField): Unit = {
    //this.outputFieldV1(field)
    this.outputFieldV2(field)
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
    val graph = field.getGraph
    println("printing graph as Field:")

    println("""
    888888ba                     .88888.
    88    `8b                   d8'   `88
    88     88 .d8888b. .d8888b. 88        .d8888b. 88d8b.d8b. .d8888b.
    88     88 88'  `88 88'  `88 88   YP88 88'  `88 88'`88'`88 88ooood8
    88    .8P 88.  .88 88.  .88 Y8.   .88 88.  .88 88  88  88 88.  ...
    8888888P  `88888P' `8888P88  `88888'  `88888P8 dP  dP  dP `88888P'
                            .88
                        d8888P
    """)

    println("m"*19*5)

    val spawn = field.getSpawnField(0)
    field.getNextField(spawn)
    println("[" + spawn.id +"] ["+ field.getNextField(spawn).id + "] [" + spawn.id+ "]")


  }


}
