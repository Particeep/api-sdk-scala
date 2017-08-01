package com.particeep.api.models.wallet

import java.time.ZonedDateTime

/**
 * Created by Noe on 01/08/2017.
 */
case class TransactionWalletSearch(
  wallet_id:          Option[String]        = None,
  debited_wallet_id:  Option[String]        = None,
  credited_wallet_id: Option[String]        = None,
  debited_amount:     Option[Int]           = None,
  debited_amount_min: Option[Int]           = None,
  debited_amount_max: Option[Int]           = None,
  credited_amount:    Option[Int]           = None,
  createdAfter:       Option[ZonedDateTime] = None,
  createdBefore:      Option[ZonedDateTime] = None,
  currency:           Option[String]        = None,
  fees:               Option[Int]           = None,
  status:             Option[String]        = None,
  tag:                Option[String]        = None,
  limit:              Option[Int]           = None,
  offset:             Option[Int]           = None
)
