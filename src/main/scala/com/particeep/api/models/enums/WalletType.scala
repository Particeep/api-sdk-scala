package com.particeep.api.models.enums

import scala.language.implicitConversions

object WalletType {

  sealed abstract class WalletType extends Enum

  case object NATURAL extends WalletType { val name: String = "NATURAL" }
  case object LEGAL extends WalletType { val name: String = "LEGAL" }

  object WalletType extends EnumHelper[WalletType] {
    def values: Set[WalletType] = Set(NATURAL, LEGAL)

    implicit def string2walletType(value: String): WalletType = get(value).getOrElse(NATURAL)
  }
}
