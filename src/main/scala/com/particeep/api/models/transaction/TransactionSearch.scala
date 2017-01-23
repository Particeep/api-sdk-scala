package com.particeep.api.models.transaction

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import com.particeep.api.models.enums.Currency.Currency
import com.particeep.api.models.enums.TransactionStatus.TransactionStatus
import play.api.libs.json.Json

case class TransactionSearch(
  start_date:      Option[ZonedDateTime]     = None,
  end_date:        Option[ZonedDateTime]     = None,
  status:          Option[TransactionStatus] = None,
  issuer_id:       Option[String]            = None,
  issuer_type:     Option[String]            = None,
  recipient_id:    Option[String]            = None,
  recipient_type:  Option[String]            = None,
  fundraise_id:    Option[String]            = None,
  fundraise_type:  Option[String]            = None,
  handled_offline: Option[Boolean]           = None,
  global_search:   Option[String]            = None,
  currency:        Option[Currency]          = None,
  sort_by:         Option[String]            = None,
  order_by:        Option[String]            = Some("asc"),
  offset:          Option[Int]               = Some(0),
  limit:           Option[Int]               = Some(30)
)

object TransactionSearch {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[TransactionSearch]
}
