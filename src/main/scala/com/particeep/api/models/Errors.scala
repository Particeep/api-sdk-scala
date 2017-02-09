package com.particeep.api.models

import play.api.libs.json.{ JsValue, Json }

case class Error(technicalCode: String, message: String, code: Option[String] = None, stack: Option[String] = None)

trait ErrorResult
case class Errors(hasError: Boolean, errors: List[Error]) extends ErrorResult
case class ParsingError(hasError: Boolean, errors: List[JsValue]) extends ErrorResult

object Errors {
  implicit val error_format = Json.format[Error]
  implicit val errors_format = Json.format[Errors]
  implicit val parsing_error_format = Json.format[ParsingError]
}
