package com.particeep.api.models.signature

/**
 * Created by Noe on 20/03/2017.
 */
case class SignatureSearch(
  status:    Option[String],
  firstname: Option[String],
  lastname:  Option[String],
  limit:     Option[Int],
  offset:    Option[Int]
)
