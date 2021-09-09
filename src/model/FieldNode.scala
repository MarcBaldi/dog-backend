package model

case class FieldNode(id: Int, description: String) {

  override def toString: String = id + "-" + description

  def isStart: Boolean = {
    description.contains("start")
  }
  def isGoal: Boolean = {
    description.contains("goal")
  }

  private[this] var _currentPawn: Option[Pawn] = None

  def currentPawn: Option[Pawn] = _currentPawn

  def currentPawn_=(value: Pawn): Unit = {
    _currentPawn = Some(value)
  }

  def resetPawn(): Unit = {
    _currentPawn = None
  }

}
