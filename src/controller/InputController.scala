package controller

import model.GameData

class InputController(val gameData: GameData) {

  def cardInput: Option[model.Card] = {
    throw new NotImplementedError()
  }

  def pawnInput: Option[model.Pawn] = {
    throw new NotImplementedError()
  }

  def outputField(field: model.NotScuffedField): Unit = {
    throw new NotImplementedError()
  }

  def announcePlayerTurn(): Unit = {
    throw new NotImplementedError()
  }

  def announceJokerMessage(): Unit = {
    throw new NotImplementedError()
  }

}
