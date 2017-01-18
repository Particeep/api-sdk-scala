package com.particeep.api.models.enums

object CalculatorType {

  sealed abstract class CalculatorType extends Enum
  case object Constant extends CalculatorType { val name: String = "Constant" }
  case object InFine extends CalculatorType { val name: String = "InFine" }

  object CalculatorType extends EnumHelper[CalculatorType] {
    def values: Set[CalculatorType] = Set(Constant, InFine)
  }
}
