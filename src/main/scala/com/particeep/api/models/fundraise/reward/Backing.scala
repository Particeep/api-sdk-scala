package com.particeep.api.models.fundraise.reward

import com.particeep.api.models.transaction.Transaction
import play.api.libs.json.Json

/**
 * Created by Noe on 26/01/2017.
 */

case class Backing(
  transaction: Transaction,
  reward:      Option[Reward]
)

object Backing {
  implicit val transaction_format = Transaction.format
  implicit val reward_format = Reward.format
  val format = Json.format[Backing]
}
