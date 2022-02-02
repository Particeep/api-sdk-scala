package com.particeep.api.models.fundraise.reward

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import play.api.libs.json.Json

/**
 * Created by Noe on 26/01/2017.
 */

case class TransactionInfo(
    amount:     Int                   = 0,
    comment:    Option[String]        = None,
    created_at: Option[ZonedDateTime]
)

object TransactionInfo {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[TransactionInfo]
}
