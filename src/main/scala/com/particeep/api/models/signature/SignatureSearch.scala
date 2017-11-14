package com.particeep.api.models.signature

/**
 * Created by Noe on 20/03/2017.
 */
case class SignatureSearch(
  status:      Option[String],
  firstName:   Option[String],
  lastName:    Option[String],
  email:       Option[String],
  fileName:    Option[String],
  target_id:   Option[String],
  target_type: Option[String]
)
