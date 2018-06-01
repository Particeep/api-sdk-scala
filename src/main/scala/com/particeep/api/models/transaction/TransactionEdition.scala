package com.particeep.api.models.transaction

import com.particeep.api.models.enums.Currency.Currency
import play.api.libs.json.{ JsObject, Json }

/**
 * Created by Noe on 25/04/2017.
 */
case class TransactionEdition(
  issuer_id:       Option[String]   = None,
  issuer_type:     Option[String]   = None,
  recipient_id:    Option[String]   = None,
  recipient_type:  Option[String]   = None,
  fundraise_id:    Option[String]   = None,
  fundraise_type:  Option[String]   = None,
  item_id:         Option[String]   = None,
  item_type:       Option[String]   = None,
  amount:          Option[Int]      = None,
  fees:            Option[Int]      = None,
  currency:        Option[Currency] = None,
  handled_offline: Option[Boolean]  = None,
  comment:         Option[String]   = None,
  tag:             Option[String]   = None,
  custom:          Option[JsObject] = None
)

object TransactionEdition {
  val format = Json.format[TransactionEdition]
}
