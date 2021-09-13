package controller

import model.GameRules

class InputController(val gameRules: GameRules) {

  def cardInput: Option[model.Card] = {
    throw new NotImplementedError()
  }

  def pawnInput: Option[model.Pawn] = {
    throw new NotImplementedError()
  }

}
