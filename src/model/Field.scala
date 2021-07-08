package model

import scala.collection.mutable.ArrayBuffer

class Field {
  var course = new ArrayBuffer[Position]()
  var entryPoints = new ArrayBuffer[Position]()



  def init(armLenght: Int = 15, playerAmount: Int = 4): Unit = {
    for (i <- 0 until armLenght * playerAmount) {
      val position = new Position(i)
      course += position

      if (i % armLenght == 0)
        entryPoints += position
    }


  }



}
