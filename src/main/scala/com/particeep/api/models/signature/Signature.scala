package com.particeep.api.models.signature

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import com.particeep.api.models.enums.SignatureStatus.SignatureStatus
import play.api.libs.json.{ JsObject, Json }

case class Signature(
  id:             String                  = "",
  created_at:     Option[ZonedDateTime]   = None,
  language:       String,
  fileUrl:        String,
  fileToSignUrl:  Option[String]          = None,
  fileName:       String,
  status:         Option[SignatureStatus] = None,
  external_id:    Option[String]          = None,
  firstName:      String,
  lastName:       String,
  email:          String,
  phone:          String,
  signedFileUrl:  Option[String]          = None,
  signature_type: Option[String]          = None,
  tag:            Option[String]          = None,
  custom:         Option[JsObject]        = None
)

object Signature {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[Signature]
}
