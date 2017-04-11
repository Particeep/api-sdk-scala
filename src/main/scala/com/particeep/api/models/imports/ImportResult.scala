package com.particeep.api.models.imports

import play.api.libs.json.Json

/**
 * Created by Noe on 10/04/2017.
 */
case class ImportResult(
  lineTreated:   Int             = 0,
  nbCreated:     Int             = 0,
  nbFail:        Int             = 0,
  lineWithError: List[LineError] = List()
)

object ImportResult {
  implicit val line_error_format = LineError.format
  val format = Json.format[ImportResult]
}

