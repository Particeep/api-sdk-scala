package com.particeep.api.models.fundraise.reward

import play.api.libs.json.Json

/**
 * Created by Noe on 26/01/2017.
 */

case class Amount(
  amount: Int = 0
)

object Amount {
  val format = Json.format[Amount]
}
