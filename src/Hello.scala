import controller.{HardScuffedMoveController, NotScuffedMoveController}
import model.{Card, Deck, GameRules, HardScuffedField4, NotScuffedField, Pawn}


object Hello extends App {
  println("Hello, World!")
  val gameRules = new GameRules()

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
  val moveC = new HardScuffedMoveController(gameRules)
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
  val currentField = NotScuffedField(gameRules)
  currentField.init()

  println(currentField.graph.size)
  for (x <- currentField.graph) {
    println(x)
  }

  var count = 0
  for (x <- currentField.graph) {
    if (x._1.toString().endsWith("start")) {
      count+=1
    }
  }
  println("start field count: "+count)

  println("Controller Move:")
  val moveC = new NotScuffedMoveController(gameRules)
  moveC.getField.initTestBoard()
  moveC.printField()
  println("pawn count: "+moveC.getField.getAllPawns.size)

  val testPawn = moveC.getRandomPawn

  moveC.move(testPawn, new Card(2))
  moveC.move(testPawn, new Card(2))
  moveC.move(testPawn, new Card(2))





}