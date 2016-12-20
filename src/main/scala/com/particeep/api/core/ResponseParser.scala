package com.particeep.api.core

import org.slf4j.LoggerFactory
import play.api.libs.json._
import play.api.libs.ws.WSResponse

import scala.compat.Platform
import scala.util.control.NonFatal
import com.particeep.api.models._

trait ResponseParser {

  import com.particeep.api.models.Errors._

  private final val log = LoggerFactory.getLogger(this.getClass)

  def parse[A](response: WSResponse)(implicit json_reads: Reads[A]): Either[ErrorResult, A] = {
    try {
      val json = response.json
      val result: Either[ErrorResult, A] = validateStandardError(json)
        .orElse(validateParsingError(json))
        .map(err => Left(err))
        .getOrElse(parseResult(json)(json_reads))
      result
    } catch {
      case NonFatal(ex) => {
        val msg = s"""Error while parsing the response :
             |status = ${response.status}
             |body = ${response.body}
           """.stripMargin
        log.error(msg, ex)
        Left(ex2error(ex))
      }
    }
  }

  private def parse[A](json: JsValue)(implicit json_reads: Reads[A]): Either[JsError, A] = {
    json.validate[A] match {
      case result: JsSuccess[A] => Right(result.get)
      case err: JsError         => Left(err)
    }
  }

  private def parseResult[A](json: JsValue)(implicit json_reads: Reads[A]): Either[ErrorResult, A] = {
    parse(json)(json_reads) match {
      case Right(success) => Right(success)
      case Left(json_err) => {
        val err = Error(
          technicalCode = json_err.errors.mkString(Platform.EOL),
          message = "unknown json error"
        )
        Left(Errors(true, List(err)))
      }
    }
  }

  private def ex2error(ex: Throwable): Errors = {
    val err = Error(
      technicalCode = ex.toString(),
      message = ex.getMessage,
      stack = Some(ex.getStackTrace().mkString("", Platform.EOL, Platform.EOL))
    )
    Errors(
      hasError = true,
      errors = List(err)
    )
  }

  private def validateStandardError(json: JsValue): Option[ErrorResult] = {
    json.validate[Errors] match {
      case result: JsSuccess[Errors] => Some(result.get)
      case _: JsError                => validateParsingError(json)
    }
  }

  private def validateParsingError(json: JsValue): Option[ParsingError] = {
    json.validate[ParsingError] match {
      case result: JsSuccess[ParsingError] => Some(result.get)
      case _: JsError                      => None
    }
  }

}
