package com.particeep.api.models.enums

object KycStatus {

  sealed abstract class KycStatus extends Enum { val order: Int }

  case object CREATED extends KycStatus { val name: String = "CREATED"; val order = 1 }
  case object ASK_VALIDATION extends KycStatus { val name: String = "ASK_VALIDATION"; val order = 2 }
  case object VALIDATED extends KycStatus { val name: String = "VALIDATED"; val order = 3 }
  case object REFUSED extends KycStatus { val name: String = "REFUSED"; val order = 4 }
  case object CANCELLED extends KycStatus { val name: String = "CANCELLED"; val order = 5 }

  object KycStatus extends EnumHelper[KycStatus] {
    def values: Set[KycStatus] = Set(CREATED, ASK_VALIDATION, VALIDATED, REFUSED, CANCELLED)
  }
}
