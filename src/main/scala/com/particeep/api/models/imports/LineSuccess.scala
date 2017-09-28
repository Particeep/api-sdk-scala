package com.particeep.api.models.imports

import play.api.libs.json._

/**
 * Created by Noe on 10/04/2017.
 */
case class LineSuccess[T](
  line:    Int,
  success: T
)

object LineSuccess {
  def reads[T](implicit fmt: Format[T]): Reads[LineSuccess[T]] = new Reads[LineSuccess[T]] {
    def reads(json: JsValue): JsResult[LineSuccess[T]] = JsSuccess(new LineSuccess[T](
      (json \ "line").as[Int],
      (json \ "success").as[T]
    ))
  }
}
