package controller

import model.GameData

class InputController(val gameData: GameData) {

  def cardInput: Option[model.Card] = {
    throw new NotImplementedError()
  }

  def pawnInput: Option[model.Pawn] = {
    throw new NotImplementedError()
  }

}
