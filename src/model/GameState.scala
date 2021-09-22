package model

object GameState extends Enumeration
{
  type GameState = Value

  // Assigning values
  val init: model.GameState.Value = Value("init")
  val preRound: model.GameState.Value = Value("pre round")
  //val round: model.GameState.Value = Value("round")
  val chooseCard: model.GameState.Value = Value("choose card")
  val chooseCardJ: model.GameState.Value = Value("choose card joker") // joker
  val choosePawn: model.GameState.Value = Value("choose pawn")
  val choosePawn2: model.GameState.Value = Value("choose second pawn") // swap pawns
  val choosePawns: model.GameState.Value = Value("choose pawns") // 7
  val chooseField: model.GameState.Value = Value("choose field")
  val postTurn: model.GameState.Value = Value("post turn")
  val finished: model.GameState.Value = Value("finished")
  val end: model.GameState.Value = Value("end")
}
