package com.particeep.api.models.wallet

case class TransactionWalletSearch(
    statuses:            Option[String] = None,
    debited_wallet_ids:  Option[String] = None,
    credited_wallet_ids: Option[String] = None,
    transactions_ids:    Option[String] = None,
    operations:          Option[String] = None,
    fundraise_ids:       Option[String] = None,
    fundraise_names:     Option[String] = None
)
