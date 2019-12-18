package com.particeep.api.models.transaction

import com.particeep.api.models.user.User
import play.api.libs.json.Json

case class Investment(
    user:        Option[User],
    transaction: Transaction
)

object Investment {
  implicit val user_format = User.format
  implicit val transaction_format = Transaction.format
  val format = Json.format[Investment]
}
