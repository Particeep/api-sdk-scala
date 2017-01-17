package com.particeep.api

import com.particeep.api.core.{ResponseParser, WSClient}
import com.particeep.api.models.ErrorResult
import com.particeep.api.models.form._
import play.api.libs.json.Json

import scala.concurrent.{ExecutionContext, Future}

trait FormCapability {
  self: WSClient =>

  val form: FormClient = new FormClient(this)
}

class FormClient(ws: WSClient) extends ResponseParser {

  private val endPoint: String = "/form"
  implicit val format = Form.format
  implicit val format_simple = SimpleForm.format
  implicit val format_creation = FormCreation.format
  implicit val format_edition = FormEdition.format
  implicit val format_answer = Answer.format
  implicit val format_answer_creation = AnswerCreation.format

  def all(timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Seq[SimpleForm]]] = {
    ws.url(s"$endPoint/all", timeout).get().map(parse[Seq[SimpleForm]])
  }

  def byId(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Form]] = {
    ws.url(s"$endPoint/$id", timeout).get().map(parse[Form])
  }

  def byIdForUser(id: String, user_id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Form]] = {
    ws.url(s"$endPoint/$id/$user_id", timeout).get().map(parse[Form])
  }

  def create(form_creation: FormCreation, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Form]] = {
    ws.url(s"$endPoint", timeout).put(Json.toJson(form_creation)).map(parse[Form])
  }

  def update(id: String, form_edition: FormEdition, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Form]] = {
    ws.url(s"$endPoint/$id").post(Json.toJson(form_edition)).map(parse[Form])
  }

  def delete(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, SimpleForm]] = {
    ws.url(s"$endPoint/$id").delete().map(parse[SimpleForm])
  }

  def answer(user_id: String, answer_creations: Seq[AnswerCreation], timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Seq[Answer]]] = {
    ws.url(s"$endPoint/answer/$user_id").put(Json.toJson(answer_creations)).map(parse[Seq[Answer]])
  }
}