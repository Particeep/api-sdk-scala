package com.particeep.api.models.enums

object SddMandateStatus {

  sealed abstract class SddMandateStatus extends Enum

  case object PENDING extends SddMandateStatus { val name: String = "PENDING" }
  case object VALIDATED extends SddMandateStatus { val name: String = "VALIDATED" }
  case object REFUSED extends SddMandateStatus { val name: String = "REFUSED" }

  object SddMandateStatus extends EnumHelper[SddMandateStatus] {
    def values: Set[SddMandateStatus] = Set(PENDING, VALIDATED, REFUSED)
  }
}
