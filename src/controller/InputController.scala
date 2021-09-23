package controller

import model.GameData

import scala.collection.mutable.ArrayBuffer

abstract class InputController(val gameData: GameData) {

  def cardInput: Option[model.Card]

  def pawnInput: Option[model.Pawn]

  def outputField(field: model.NotScuffedField): Unit

  def outputCards(hand: ArrayBuffer[model.Card]): Unit

  def announcePlayerTurn(): Unit

  def announceJokerMessage(): Unit

  def announceFinishedMessage(player: Int): Unit

  def announceEndMessage(player: Int): Unit

}
