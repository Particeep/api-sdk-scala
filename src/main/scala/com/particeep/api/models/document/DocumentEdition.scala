package com.particeep.api.models.document

import play.api.libs.json.Json

/**
 * Created by Noe on 17/02/2017.
 */
case class DocumentEdition(
  name:        Option[String],
  description: Option[String]
)

object DocumentEdition {
  val format = Json.format[DocumentEdition]
}
