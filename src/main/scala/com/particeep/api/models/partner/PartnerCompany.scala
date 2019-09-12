package com.particeep.api.models.partner

import java.time.ZonedDateTime

import ai.x.play.json.Jsonx
import com.particeep.api.core.Formatter
import com.particeep.api.models.enums.OriasStatus.OriasStatus
import com.particeep.api.models.enums.RegulatoryStatus.RegulatoryStatus
import play.api.libs.json.JsObject

case class PartnerCompany(
  id:                    String,
  created_at:            ZonedDateTime,
  user_id:               String,
  is_rcs_verified:       Option[Boolean]          = None,
  rcs_registration_year: Option[String]           = None,
  rcs_city:              Option[String]           = None,
  regulatory_status:     Option[RegulatoryStatus] = None,
  orias_status:          Option[OriasStatus]      = None,
  orias_number:          Option[String]           = None,
  regafi_number:         Option[String]           = None,
  geco_number:           Option[String]           = None,
  company_business_name: Option[String]           = None,
  siren:                 Option[String]           = None,
  siret:                 Option[String]           = None,
  tva_intra:             Option[String]           = None,
  legal_representative:  Option[String]           = None,
  legal_status:          Option[String]           = None,
  contact_first_name:    Option[String]           = None,
  contact_last_name:     Option[String]           = None,
  contact_email:         Option[String]           = None,
  url:                   Option[String]           = None,
  tag:                   Option[String]           = None,
  custom:                Option[JsObject]         = None
)

object PartnerCompany {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Jsonx.formatCaseClass[PartnerCompany]
}
