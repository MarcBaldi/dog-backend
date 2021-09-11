package model

object FieldType extends Enumeration
{
  type FieldType = Value

  // Assigning values
  val field: model.FieldType.Value = Value("Field")
  val spawn: model.FieldType.Value = Value("Spawn")
  val start: model.FieldType.Value = Value("Start")
  val goal: model.FieldType.Value = Value("Goal")

}
