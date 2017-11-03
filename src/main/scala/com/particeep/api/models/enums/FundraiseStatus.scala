package com.particeep.api.models.enums

import scala.language.implicitConversions

object FundraiseStatus {

  sealed abstract class FundraiseStatus extends Enum
  case object INIT extends FundraiseStatus { val name: String = "INIT" }
  case object UNDER_REVIEW extends FundraiseStatus { val name: String = "UNDER_REVIEW" }
  case object RUNNING extends FundraiseStatus { val name: String = "RUNNING" }
  case object SUCCEEDED extends FundraiseStatus { val name: String = "SUCCEEDED" }
  case object REFUND_ONGOING extends FundraiseStatus { val name: String = "REFUND_ONGOING" }
  case object REFUNDED extends FundraiseStatus { val name: String = "REFUNDED" }
  case object DELETED extends FundraiseStatus { val name: String = "DELETED" }

  object FundraiseStatus extends EnumHelper[FundraiseStatus] {
    def values: Set[FundraiseStatus] = Set(INIT, UNDER_REVIEW, RUNNING, SUCCEEDED, REFUND_ONGOING, REFUNDED, DELETED)

    implicit def stringToFundraiseStatus(value: String): FundraiseStatus = get(value).getOrElse(INIT)
  }
}
