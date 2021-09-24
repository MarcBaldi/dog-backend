package controller

import model.GameData
import sun.security.ec.point.ProjectivePoint.Mutable

import scala.collection.mutable.ArrayBuffer

abstract class InputController(val gameData: GameData) {

  def cardInput: Option[model.Card]

  def pawnInput: Option[model.Pawn]

  def fieldInput: Option[Int]

  def outputPossibleFields(fields: ArrayBuffer[model.FieldNode]): Unit

  def outputField(field: model.NotScuffedField): Unit

  def outputCards(hand: ArrayBuffer[model.Card]): Unit

  def announcePlayerTurn(): Unit

  def announceJokerMessage(): Unit

  def announceFinishedMessage(player: Int): Unit

  def announceEndMessage(player: Int): Unit

}
