package com.particeep.api.models.payment

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import play.api.libs.json.Json

case class ScheduledPaymentSearch(
    start_payment_date: Option[ZonedDateTime] = None,
    end_payment_date:   Option[ZonedDateTime] = None,
    status:             Option[String]        = None,
    issuer_id:          Option[String]        = None,
    issuer_type:        Option[String]        = None,
    recipient_id:       Option[String]        = None,
    recipient_type:     Option[String]        = None,
    parent_id:          Option[String]        = None,
    parent_type:        Option[String]        = None,
    tag:                Option[String]        = None,
    sort_by:            Option[String]        = None,
    order_by:           Option[String]        = Some("asc"),
    offset:             Option[Int]           = Some(0),
    limit:              Option[Int]           = Some(30)
)

object ScheduledPaymentSearch {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[ScheduledPaymentSearch]
}
