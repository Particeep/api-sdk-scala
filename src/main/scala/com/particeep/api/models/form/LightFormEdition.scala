package com.particeep.api.models.form

import play.api.libs.json.Json

/**
 * Created by Noe on 06/06/2017.
 */
case class LightFormEdition(
  name:        Option[String] = None,
  description: Option[String] = None,
  tag:         Option[String] = None
)

object LightFormEdition {
  val format = Json.format[LightFormEdition]
}
