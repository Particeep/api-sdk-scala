package com.particeep.api.models.fundraise.reward

import com.particeep.api.models.transaction.Transaction
import com.particeep.api.models.user.User
import play.api.libs.json.Json

/**
 * Created by Noe on 26/01/2017.
 */

case class Backer(
    transaction: Transaction,
    user:        User,
    reward:      Option[Reward]
)

object Backer {
  implicit val transaction_format = Transaction.format
  implicit val user_format = User.format
  implicit val reward_format = Reward.format
  val format = Json.format[Backer]
}
