package com.particeep.api.models.transaction

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import com.particeep.api.models.enums.Currency.Currency
import com.particeep.api.models.enums.PaymentMethod.PaymentMethod
import org.cvogt.play.json.Jsonx
import play.api.libs.json.JsObject

/**
 * Created by Noe on 30/06/2017.
 */
case class TransactionData(
  id:                    String,
  created_at:            Option[ZonedDateTime] = None,
  issuer_id:             Option[String]        = None,
  issuer_type:           Option[String]        = None,
  issuer_name:           Option[String]        = None,
  recipient_id:          Option[String]        = None,
  recipient_type:        Option[String]        = None,
  recipient_name:        Option[String]        = None,
  fundraise_id:          Option[String]        = None,
  fundraise_type:        Option[String]        = None,
  item_id:               Option[String]        = None,
  item_type:             Option[String]        = None,
  amount:                Option[Int]           = None,
  fees:                  Option[Int]           = None,
  currency:              Option[Currency]      = None,
  status:                Option[String]        = None,
  payment_method:        Option[PaymentMethod] = None,
  external_reference:    Option[String]        = None,
  handled_offline:       Option[Boolean]       = None,
  comment:               Option[String]        = None,
  targeting_roles:       Option[String]        = None,
  partner_flat_fees:     Option[Int]           = None,
  partner_variable_fees: Option[Double]        = None,
  tag:                   Option[String]        = None,
  custom:                Option[JsObject]      = None
)

object TransactionData {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Jsonx.formatCaseClass[TransactionData]
}
