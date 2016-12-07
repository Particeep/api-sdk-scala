package com.particeep.api.models

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

case class PaginateSequence[T](
  total_size: Int     = 0,
  data:       List[T] = List()
)

object PaginateSequence {
  implicit def searchResultsFormat[T](implicit f: Format[T]): Format[PaginateSequence[T]] = (
    (__ \ "total_size").format[Int] ~
    (__ \ "data").format[List[T]]
  )(PaginateSequence.apply, unlift(PaginateSequence.unapply))
}
