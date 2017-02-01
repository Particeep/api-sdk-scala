package com.particeep.api.models.news

import com.particeep.api.models.PaginatedSequence
import play.api.libs.json.Json

case class NewsPrevAndNext(
  prev: PaginatedSequence[News],
  next: PaginatedSequence[News]
)

object NewsPrevAndNext {
  implicit val news_format = News.format
  val format = Json.format[NewsPrevAndNext]
}
