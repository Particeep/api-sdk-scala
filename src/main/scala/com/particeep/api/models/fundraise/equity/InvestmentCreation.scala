package com.particeep.api.models.fundraise.equity

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import play.api.libs.json.Json

case class InvestmentCreation(
  user_id:    String,
  amount:     Int,
  created_at: Option[ZonedDateTime]
)

object InvestmentCreation {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[InvestmentCreation]
}
