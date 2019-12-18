package com.particeep.api.models.enums

import scala.language.implicitConversions

object FundStatus {

  sealed abstract class FundStatus extends Enum

  case object INIT extends FundStatus { val name: String = "INIT" }
  case object RUNNING extends FundStatus { val name: String = "RUNNING" }
  case object CLOSING extends FundStatus { val name: String = "CLOSING" }

  object FundStatus extends EnumHelper[FundStatus] {
    def values: Set[FundStatus] = Set(INIT, RUNNING, CLOSING)

    implicit def stringToFundStatus(value: String): FundStatus = get(value.toUpperCase).getOrElse(INIT)
  }

}
