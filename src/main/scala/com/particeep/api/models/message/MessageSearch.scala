package com.particeep.api.models.message

import play.api.libs.json.Json

case class MessageSearch(
                        title: Option[String] = None,
                        author_id: Option[String] = None,
                        target_id: Option[String] = None,
                        target_type: Option[String] = None,
                        offset: Option[Int] = Some(0),
                        limit: Option[Int] = Some(30)
                        )

object MessageSearch {
  val format = Json.format[MessageSearch]
}
