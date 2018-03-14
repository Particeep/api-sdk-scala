package com.particeep.api.models.enums

object TransactionWalletOperation {

  sealed abstract class TransactionWalletOperation extends Enum
  case object WALLET_PAYMENT extends TransactionWalletOperation { val name: String = "WALLET_PAYMENT" }
  case object CASHIN_CB extends TransactionWalletOperation { val name: String = "CASHIN_CB" }
  case object CASHIN_SDD extends TransactionWalletOperation { val name: String = "CASHIN_SDD" }
  case object CASHIN_TRANSFER extends TransactionWalletOperation { val name: String = "CASHIN_TRANSFER" }
  case object CASHIN_CHECK extends TransactionWalletOperation { val name: String = "CASHIN_CHECK" }
  case object CASHOUT extends TransactionWalletOperation { val name: String = "CASHOUT" }
  case object REFUND_WALLET_PAYMENT extends TransactionWalletOperation { val name: String = "REFUND_WALLET_PAYMENT" }
  case object SCHEDULED_WALLET_PAYMENT extends TransactionWalletOperation { val name: String = "SCHEDULED_WALLET_PAYMENT" }
  case object SCHEDULED_TAX_WALLET_PAYMENT extends TransactionWalletOperation { val name: String = "SCHEDULED_TAX_WALLET_PAYMENT" }

  object TransactionWalletOperation extends EnumHelper[TransactionWalletOperation] {
    def values: Set[TransactionWalletOperation] = Set(WALLET_PAYMENT, CASHIN_CB, CASHIN_SDD, CASHIN_TRANSFER,
      CASHIN_CHECK, CASHOUT, REFUND_WALLET_PAYMENT, SCHEDULED_WALLET_PAYMENT, SCHEDULED_TAX_WALLET_PAYMENT)

    def stringToTransactionWalletOperation(value: String): TransactionWalletOperation = get(value.toUpperCase).getOrElse(WALLET_PAYMENT)
  }
}
