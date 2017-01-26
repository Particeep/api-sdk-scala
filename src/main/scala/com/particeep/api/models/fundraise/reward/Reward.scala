package com.particeep.api.models.fundraise.reward

import java.time.ZonedDateTime

import play.api.libs.json.Json

/**
 * Created by Noe on 26/01/2017.
 */
case class Reward(
  id:           Option[String]        = None,
  name:         Option[String]        = None,
  price:        Int                   = 0,
  img_url:      Option[String]        = None,
  quantity:     Option[Int]           = None,
  delivered_at: Option[ZonedDateTime] = None,
  description:  Option[String]        = None
)

object Reward {
  val format = Json.format[Reward]
}
