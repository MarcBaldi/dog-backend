package model

object GameState extends Enumeration
{
  type GameState = Value

  // Assigning values
  val init: model.GameState.Value = Value("init")
  val preRound: model.GameState.Value = Value("pre round")
  //val round: model.GameState.Value = Value("round")
  val chooseCard: model.GameState.Value = Value("choose card")
  val choosePawn: model.GameState.Value = Value("choose pawn")
  val postTurn: model.GameState.Value = Value("post turn")
  val finished: model.GameState.Value = Value("finished")
  val end: model.GameState.Value = Value("end")
}
