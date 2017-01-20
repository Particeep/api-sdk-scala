package com.particeep.api.models.enums

object PaymentMethod {

  sealed abstract class PaymentMethod extends Enum
  case object WALLET extends PaymentMethod { val name: String = "WALLET" }
  case object CREDIT_CARD extends PaymentMethod { val name: String = "CREDIT_CARD" }
  case object DIRECT_CASHIN extends PaymentMethod { val name: String = "DIRECT_CASHIN" }

  object PaymentMethod extends EnumHelper[PaymentMethod] {
    def values: Set[PaymentMethod] = Set(WALLET, CREDIT_CARD, DIRECT_CASHIN)
  }
}
