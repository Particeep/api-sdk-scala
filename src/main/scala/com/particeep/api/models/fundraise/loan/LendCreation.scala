package com.particeep.api.models.fundraise.loan

import play.api.libs.json.Json

case class LendCreation(
  user_id: String,
  amount:  Int
)

object LendCreation {
  val format = Json.format[LendCreation]
}
