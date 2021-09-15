import com.typesafe.scalalogging.Logger
import controller.{HardScuffedMoveController, NotScuffedMoveController}
import model.{Card, Deck, GameData, HardScuffedField4, NotScuffedField, Pawn}


object Hello extends App {

  val logger = Logger("TestClass Hello")

  println("Hello, World!")
  val gameData = new GameData()

  println("Model Deck:")
  var deck = new Deck()
  deck.shuffle()
  println(deck.cards.size)
  println(deck.draw(4))
  println(deck.cards.size)
  //println(deck.cards)
/*
  println("Model Field:")
  var field = new HardScuffedField4()
  field.init()
  println(field.playerPositions)

  println("Controller Move:")
  val moveC = new HardScuffedMoveController(gameData)
  moveC.sendPawnOnField(Pawn(0, 0))

  println(moveC.field.playerPositions)
  moveC.move(Pawn(0,0), new Card(2))
  println(moveC.field.playerPositions)
  moveC.move(Pawn(0,0), new Card(8))

  println(moveC.field.playerPositions)

  moveC.sendPawnHome(Pawn(0, 0))

  println(moveC.field.playerPositions)
*/
  println("Actually NotScuffed Field:")
  val currentField = NotScuffedField(gameData)
  currentField.init()

  println(currentField.getGraph.size)
  for (x <- currentField.getGraph) {
    println(x)
  }

  var count = 0
  for (x <- currentField.getGraph.keys) {
    if (x.fieldType == model.FieldType.start) {
      count+=1
    }
  }
  println("start field count: "+count)

  println("Controller Move:")
  val moveC = new NotScuffedMoveController(gameData, false)
  moveC.getField.initTestBoard()
  //moveC.printField()
  println("pawn count: "+moveC.getField.getAllPawns.size)

  val testPawn = moveC.getRandomPawn

  moveC.move(testPawn, Card(1))
  println("(1) moved:" + testPawn + " to: " + moveC.getField.getField(testPawn))
  moveC.move(testPawn, Card(2))
  println("(2) moved:" + testPawn + " to: " + moveC.getField.getField(testPawn))
  moveC.move(testPawn, Card(3))
  println("(3) moved:" + testPawn + " to: " + moveC.getField.getField(testPawn))
  moveC.move(testPawn, Card(4))
  println("(4) moved:" + testPawn + " to: " + moveC.getField.getField(testPawn))
  moveC.move(testPawn, Card(5))
  println("(5) moved:" + testPawn + " to: " + moveC.getField.getField(testPawn))
  moveC.move(testPawn, Card(6))
  println("(6) moved:" + testPawn + " to: " + moveC.getField.getField(testPawn))
  moveC.move(testPawn, Card(8))
  println("(8) moved:" + testPawn + " to: " + moveC.getField.getField(testPawn))
  moveC.move(testPawn, Card(9))
  println("(9) moved:" + testPawn + " to: " + moveC.getField.getField(testPawn))
  moveC.move(testPawn, Card(10))
  println("(10) moved:" + testPawn + " to: " + moveC.getField.getField(testPawn))
  moveC.move(testPawn, Card(11))
  println("(11) moved:" + testPawn + " to: " + moveC.getField.getField(testPawn))
  moveC.move(testPawn, Card(12))
  println("(12) moved:" + testPawn + " to: " + moveC.getField.getField(testPawn))
  moveC.move(testPawn, Card(13))
  println("(13) moved:" + testPawn + " to: " + moveC.getField.getField(testPawn))
  moveC.move(testPawn, Card(14))
  println("(14) moved:" + testPawn + " to: " + moveC.getField.getField(testPawn))
  moveC.move(testPawn, Card(7))
  println("(7) moved:" + testPawn + " to: " + moveC.getField.getField(testPawn))

  logger.debug("Logger is working.")
  moveC.demo()


}