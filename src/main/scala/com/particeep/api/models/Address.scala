package com.particeep.api.models

import play.api.libs.json._

case class Address(
    number:  Option[String] = None,
    street:  Option[String] = None,
    zip:     Option[String] = None,
    city:    Option[String] = None,
    country: Option[String] = None,
    tag:     Option[String] = None
)

object Address {
  implicit val format = Json.format[Address]
}
