package model

class Card(val id: Int) {
  def getValue: Int = id % 14

  def getDescription: String = {
    this.getValue match {
      case 1  => "Ass"
      case 2  => "2"
      case 3  => "3"
      case 4  => "4"
      case 5  => "5"
      case 6  => "6"
      case 7  => "7"
      case 8  => "8"
      case 9  => "9"
      case 10 => "10"
      case 11 => "Booba"
      case 12 => "Waifu"
      case 13 => "König"
      case 0 => "Joker"
      case _  => "Invalid Card"
    }
  }

  override def toString: String = this.getDescription
}
