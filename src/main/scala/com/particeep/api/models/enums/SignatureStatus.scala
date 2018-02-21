package com.particeep.api.models.enums

object SignatureStatus {

  sealed abstract class SignatureStatus extends Enum

  case object CANCELED extends SignatureStatus { val name: String = "CANCELED" }
  case object EXPIRED extends SignatureStatus { val name: String = "EXPIRED" }
  case object FAILED extends SignatureStatus { val name: String = "FAILED" }
  case object PENDING_VALIDATION extends SignatureStatus { val name: String = "PENDING_VALIDATION" }
  case object READY extends SignatureStatus { val name: String = "READY" }
  case object WAITING extends SignatureStatus { val name: String = "WAITING" }
  case object COMPLETED extends SignatureStatus { val name: String = "COMPLETED" }

  object SignatureStatus extends EnumHelper[SignatureStatus] {
    def values: Set[SignatureStatus] = Set(READY, EXPIRED, COMPLETED, CANCELED, FAILED, PENDING_VALIDATION, WAITING)
  }
}
