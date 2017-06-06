package com.particeep.api.models.form

import play.api.libs.json.Json

/**
 * Created by Noe on 06/06/2017.
 */
case class LightFormEdition(
  name:        Option[String],
  description: Option[String],
  tag:         Option[String]
)

object LightFormEdition {
  val format = Json.format[LightFormEdition]
}
