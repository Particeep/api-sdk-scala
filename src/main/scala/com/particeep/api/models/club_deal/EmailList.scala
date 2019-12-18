package com.particeep.api.models.club_deal

import play.api.libs.json.Json

/**
 * Created by Noe on 05/07/2017.
 */
case class EmailList(
    emails: List[String] = List()
)

object EmailList {
  val format = Json.format[EmailList]
}
