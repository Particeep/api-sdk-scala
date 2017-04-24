package com.particeep.api.models.fundraise.reward

import play.api.libs.json.Json

/**
 * Created by Noe on 26/01/2017.
 */

case class TransactionInfo(
  amount:  Int            = 0,
  comment: Option[String] = None
)

object TransactionInfo {
  val format = Json.format[TransactionInfo]
}
