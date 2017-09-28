package com.particeep.api.models.imports

import play.api.libs.json._

/**
 * Created by Noe on 10/04/2017.
 */
case class ImportResult[T](
  lineTreated:   Int                  = 0,
  nbCreated:     Int                  = 0,
  nbFail:        Int                  = 0,
  lineWithError: List[LineError]      = List(),
  lineOnSuccess: List[LineSuccess[T]] = List()
)

object ImportResult {
  implicit val line_error_format = LineError.format
  implicit def line_success_format[T](implicit fmt: Format[T]) = LineSuccess.format[T]
  def format[T](implicit fmt: Format[T]): Format[ImportResult[T]] = new Format[ImportResult[T]] {
    def reads(json: JsValue): JsResult[ImportResult[T]] = JsSuccess(new ImportResult[T](
      (json \ "lineTreated").as[Int],
      (json \ "nbCreated").as[Int],
      (json \ "nbFail").as[Int],
      (json \ "lineWithError").as[List[LineError]],
      (json \ "lineOnSuccess").as[List[LineSuccess[T]]]
    ))
    def writes(ir: ImportResult[T]) = JsObject(Seq(
      "lineTreated" -> JsNumber(ir.lineTreated),
      "nbCreated" -> JsNumber(ir.nbCreated),
      "nbFail" -> JsNumber(ir.nbFail),
      "lineWithError" -> Json.toJson(ir.lineWithError),
      "lineOnSuccess" -> Json.toJson(ir.lineOnSuccess)
    ))
  }
}

