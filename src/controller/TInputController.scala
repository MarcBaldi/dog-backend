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


}
