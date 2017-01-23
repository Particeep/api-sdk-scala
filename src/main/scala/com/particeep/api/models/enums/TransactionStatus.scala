package com.particeep.api.models.enums

import scala.language.implicitConversions

object TransactionStatus {

  sealed abstract class TransactionStatus extends Enum
  case object PENDING extends TransactionStatus { val name: String = "PENDING" }
  case object SUCCEEDED extends TransactionStatus { val name: String = "SUCCEEDED" }
  case object REFUNDED extends TransactionStatus { val name: String = "REFUNDED" }
  case object CANCELLED extends TransactionStatus { val name: String = "CANCELLED" }

  object TransactionStatus extends EnumHelper[TransactionStatus] {
    def values: Set[TransactionStatus] = Set(PENDING, SUCCEEDED, REFUNDED, CANCELLED)

    implicit def stringToTransactionStatus(value: String): TransactionStatus = get(value).getOrElse(PENDING)
  }
}
