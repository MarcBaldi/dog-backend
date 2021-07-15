import controller.HardScuffedMoveController
import model.{Card, Deck, GameRules, HardScuffedField4, Pawn}


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


}