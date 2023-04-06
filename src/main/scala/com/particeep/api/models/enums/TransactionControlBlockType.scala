package com.particeep.api.models.enums

sealed trait TransactionControlBlockType extends ControlBlockType

object TransactionControlBlockType extends EnumHelper[TransactionControlBlockType] {

  case object INVESTOR extends TransactionControlBlockType { val name: String = "INVESTOR" }
  case object CO_ISSUER extends TransactionControlBlockType { val name: String = "CO_ISSUER" }
  case object PARTNER extends TransactionControlBlockType { val name: String = "PARTNER" }
  case object USUFRUCTUARY extends TransactionControlBlockType { val name: String = "USUFRUCTUARY" }
  case object BANK_ACCOUNT extends TransactionControlBlockType { val name: String = "BANK_ACCOUNT" }
  case object TRANSACTION extends TransactionControlBlockType { val name: String = "TRANSACTION" }
  case object DOCUMENTS extends TransactionControlBlockType { val name: String = "DOCUMENTS" }
  case object QUESTION extends TransactionControlBlockType { val name: String = "QUESTION" }

  val values: Set[TransactionControlBlockType] = {
    Set(
      INVESTOR,
      CO_ISSUER,
      PARTNER,
      USUFRUCTUARY,
      BANK_ACCOUNT,
      TRANSACTION,
      DOCUMENTS,
      QUESTION
    )
  }

}
