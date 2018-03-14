package com.particeep.api.models.enums

object TransactionWalletStatus {

  sealed abstract class TransactionWalletStatus extends Enum

  case object PENDING extends TransactionWalletStatus { val name: String = "PENDING" }
  case object FAILED extends TransactionWalletStatus { val name: String = "FAILED" }
  case object VALIDATED extends TransactionWalletStatus { val name: String = "VALIDATED" }

  object TransactionWalletStatus extends EnumHelper[TransactionWalletStatus] {
    def values: Set[TransactionWalletStatus] = Set(PENDING, FAILED, VALIDATED)

    def stringToTransactionWalletStatus(value: String): TransactionWalletStatus = get(value.toUpperCase).getOrElse(PENDING)
  }

}
