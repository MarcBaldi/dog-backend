package model

import model.FieldType.FieldType

case class FieldNode(id: Int, description: String) {

  override def toString: String = id + "-" + description + "-[" + currentPawn + "]"

  def isStart: Boolean = {
    description.contains("start")
  }
  def isGoal: Boolean = {
    description.contains("goal")
  }

  // TYPE
  private[this] var _fieldType: Option[FieldType] = None

  // FieldType will always be set at the time of requesting the value
  def fieldType: FieldType = _fieldType.get

  def fieldType_=(value: FieldType): Unit = {
    _fieldType = Some(value)
  }

  // PLAYER
  private[this] var _player: Option[Int] = None

  def player: Option[Int] = _player

  def player_=(value: Int): Unit = {
    _player = Some(value)
  }

  def resetPlayer(): Unit = {
    _player = None
  }

  // PAWN
  private[this] var _currentPawn: Option[Pawn] = None

  def currentPawn: Option[Pawn] = _currentPawn

  def currentPawn_=(value: Pawn): Unit = {
    _currentPawn = Some(value)
  }

  def resetPawn(): Unit = {
    _currentPawn = None
  }

}
