package model

import model.FieldType.FieldType

case class FieldNode(id: Int) {
  override def toString: String = s"$id-$fieldType-[$currentPawn]-P" + this.getIfPlayer

  // TYPE
  private[this] var _fieldType: Option[FieldType] = Some(FieldType.field)

  // FieldType will always be set at the time of requesting the value
  def fieldType: FieldType = _fieldType.get

  def fieldType_=(value: FieldType): Unit = {
    _fieldType = Some(value)
  }

  // PLAYER - owner of this start,spawn or goal field
  private[this] var _player: Option[Int] = None

  def player: Option[Int] = _player

  def player_=(value: Int): Unit = {
    _player = Some(value)
  }

  def resetPlayer(): Unit = {
    _player = None
  }

  def getIfPlayer: String = {
    if (player.isEmpty) return ""
    player.get.toString
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
