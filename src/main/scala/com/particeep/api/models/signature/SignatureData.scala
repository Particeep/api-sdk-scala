package com.particeep.api.models.signature

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import org.cvogt.play.json.Jsonx
import play.api.libs.json.JsObject

case class SignatureData(
  id:            String,
  created_at:    Option[ZonedDateTime] = None,
  language:      Option[String]        = None,
  fileUrl:       Option[String]        = None,
  fileToSignUrl: Option[String]        = None,
  signedFileUrl: Option[String]        = None,
  fileName:      Option[String]        = None,
  status:        Option[String]        = None,
  external_id:   Option[String]        = None,
  firstName:     Option[String]        = None,
  lastName:      Option[String]        = None,
  email:         Option[String]        = None,
  phone:         Option[String]        = None,
  target_id:     Option[String]        = None,
  target_type:   Option[String]        = None,
  signer_order:  Option[Int]           = None,
  total_signer:  Option[Int]           = None,
  group_id:      Option[String]        = None,
  partner_ids:   Option[String]        = None,
  tag:           Option[String]        = None,
  custom:        Option[JsObject]      = None
)

object SignatureData {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Jsonx.formatCaseClass[SignatureData]
}
