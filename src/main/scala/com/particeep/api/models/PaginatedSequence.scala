package com.particeep.api.models

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

case class PaginatedSequence[T](
  total_size: Int     = 0,
  data:       List[T] = List()
)

object PaginatedSequence {

  implicit def paginatedSequenceFormat[T](implicit f: Format[T]): Format[PaginatedSequence[T]] = (
    (__ \ "total_size").format[Int] ~
    (__ \ "data").format[List[T]]
  )(PaginatedSequence.apply, unlift(PaginatedSequence.unapply))
}
