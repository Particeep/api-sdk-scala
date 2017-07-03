package com.particeep.api.models.transaction

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import org.cvogt.play.json.Jsonx

case class TransactionSearch(
  start_date:       Option[ZonedDateTime] = None,
  end_date:         Option[ZonedDateTime] = None,
  issuer_id:        Option[String]        = None,
  issuer_type:      Option[String]        = None,
  issuer_email:     Option[String]        = None,
  fundraise_id:     Option[String]        = None,
  fundraise_type:   Option[String]        = None,
  fundraise_name:   Option[String]        = None,
  recipient_id:     Option[String]        = None,
  recipient_type:   Option[String]        = None,
  item_id:          Option[String]        = None,
  item_type:        Option[String]        = None,
  amount:           Option[Int]           = None,
  fees:             Option[Int]           = None,
  currency:         Option[String]        = None,
  status:           Option[String]        = None,
  payment_method:   Option[String]        = None,
  handled_offline:  Option[Boolean]       = None,
  comment:          Option[String]        = None,
  signature_id:     Option[String]        = None,
  signature_status: Option[String]        = None,
  sort_by:          Option[String]        = None,
  order_by:         Option[String]        = Some("asc"),
  global_search:    Option[String]        = None,
  limit:            Option[Int]           = Some(30),
  offset:           Option[Int]           = Some(0)
)

object TransactionSearch {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Jsonx.formatCaseClass[TransactionSearch]
}
