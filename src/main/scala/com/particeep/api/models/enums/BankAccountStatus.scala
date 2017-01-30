package com.particeep.api.models.enums

object BankAccountStatus {

  sealed abstract class BankAccountStatus extends Enum

  case object PENDING extends BankAccountStatus { val name: String = "PENDING" }
  case object VALIDATED extends BankAccountStatus { val name: String = "VALIDATED" }
  case object REFUSED extends BankAccountStatus { val name: String = "REFUSED" }

  object BankAccountStatus extends EnumHelper[BankAccountStatus] {
    def values: Set[BankAccountStatus] = Set(PENDING, VALIDATED, REFUSED)
  }
}
