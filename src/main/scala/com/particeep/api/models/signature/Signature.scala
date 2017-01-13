package com.particeep.api.models.signature

import play.api.libs.json.Json

case class Signature(
  id:            String         = "",
  language:      String,
  fileUrl:       String,
  fileToSignUrl: Option[String] = None,
  fileName:      String,
  status:        Option[String] = None,
  external_id:   Option[String] = None,
  firstName:     String,
  lastName:      String,
  email:         String,
  phone:         String,
  signedFileUrl: Option[String] = None
)

object Signature {
  val format = Json.format[Signature]
}
