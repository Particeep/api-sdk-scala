package com.particeep.api.models.enums

object WalletStatus {

  sealed abstract class WalletStatus extends Enum

  case object LIGHT extends WalletStatus { val name: String = "LIGHT" }
  case object CONFIRMED extends WalletStatus { val name: String = "CONFIRMED" }

  object WalletStatus extends EnumHelper[WalletStatus] {
    def values: Set[WalletStatus] = Set(LIGHT, CONFIRMED)
  }
}
