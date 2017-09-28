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
  def format[T](implicit fmt: Format[T]): Format[LineSuccess[T]] = new Format[LineSuccess[T]] {
    def reads(json: JsValue): JsResult[LineSuccess[T]] = JsSuccess(new LineSuccess[T](
      (json \ "line").as[Int],
      (json \ "success").as[T]
    ))
    def writes(l: LineSuccess[T]) = JsObject(Seq(
      "line" -> JsNumber(l.line),
      "success" -> Json.toJson(l.success)
    ))
  }
}
