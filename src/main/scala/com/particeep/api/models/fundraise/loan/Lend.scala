package com.particeep.api.models.fundraise.loan

import com.particeep.api.models.transaction.Transaction
import com.particeep.api.models.user.User
import play.api.libs.json.Json

case class Lend(
    transaction: Transaction,
    user:        Option[User]
)

object Lend {
  implicit val transaction_format = Transaction.format
  implicit val user_format = User.format
  val format = Json.format[Lend]
}
