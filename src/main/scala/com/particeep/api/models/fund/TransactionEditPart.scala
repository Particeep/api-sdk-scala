package com.particeep.api.models.fund

import play.api.libs.json.Json

case class TransactionEditPart(
    transaction_ids: Seq[String],
    price_per_share: Int
)

object TransactionEditPart {
  val format = Json.format[TransactionEditPart]
}

