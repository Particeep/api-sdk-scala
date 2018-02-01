package com.particeep.api.models.signature

/**
 * Created by Noe on 20/03/2017.
 */
case class SignatureSearch(
  status:      Option[String] = None,
  firstName:   Option[String] = None,
  lastName:    Option[String] = None,
  email:       Option[String] = None,
  fileName:    Option[String] = None,
  target_id:   Option[String] = None,
  target_type: Option[String] = None
)
