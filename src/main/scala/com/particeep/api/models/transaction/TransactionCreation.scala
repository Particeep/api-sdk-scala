package com.particeep.api.models.transaction

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import com.particeep.api.models.enums.Currency.{ Currency, EUR }
import play.api.libs.json.{ JsObject, Json }

/**
 * Created by Noe on 03/04/2017.
 */
case class TransactionCreation(
  created_at:     Option[ZonedDateTime] = None,
  issuer_id:      String                = "",
  issuer_type:    String                = "",
  recipient_id:   String                = "",
  recipient_type: String                = "",
  fundraise_id:   Option[String]        = None,
  fundraise_type: Option[String]        = None,
  item_id:        Option[String]        = None,
  item_type:      Option[String]        = None,
  amount:         Int                   = 0,
  fees:           Int                   = 0,
  currency:       Currency              = EUR,
  comment:        Option[String]        = None,
  tag:            Option[String]        = None,
  custom:         Option[JsObject]      = None
)

object TransactionCreation {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[TransactionCreation]
}
