package com.particeep.api.models.enums

object InvestorType {

  sealed abstract class InvestorType extends Enum

  case object NATURAL extends InvestorType { val name: String = "NATURAL" }
  case object LEGAL extends InvestorType { val name: String = "LEGAL" }

  object InvestorType extends EnumHelper[InvestorType] {
    def values: Set[InvestorType] = Set(NATURAL, LEGAL)
  }
}
