package com.particeep.api.models.imports

import play.api.libs.json.Json

/**
 * Created by Noe on 10/04/2017.
 */
case class LineError(
  line:  Int,
  error: String
)

object LineError {
  val format = Json.format[LineError]
}
