import model.{Deck, Field}


object Hello extends App {
  println("Hello, World!")
  var deck = new Deck()
  deck.shuffle()
  println(deck.cards.size)
  println(deck.draw(4))
  println(deck.cards.size)
  println(deck.cards)

  println("FIELD:")

  var field = new Field()
  field.init()

  println(field.course)
  println(field.entryPoints)

  println(field.playerStartPositions)
  println(field.playerEndPositions)

  println("Controller:")


}