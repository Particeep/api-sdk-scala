package com.particeep.api

import com.particeep.api.core._
import com.particeep.api.models.ErrorResult
import com.particeep.api.models.form._
import play.api.libs.json.Json

import scala.concurrent.{ ExecutionContext, Future }

trait FormCapability {
  self: WSClient =>

  val form: FormClient = new FormClient(this)
  def form(credentials: ApiCredential): FormClient = new FormClient(this, Some(credentials))
}

object FormClient {
  private val endPoint: String = "/form"
  private implicit val format = Form.format
  private implicit val format_simple = SimpleForm.format
  private implicit val format_creation = FormCreation.format
  private implicit val format_edition = FormEdition.format
  private implicit val format_light_edition = LightFormEdition.format
  private implicit val format_answer = Answer.format
  private implicit val format_answer_creation = AnswerCreation.format
  private implicit val format_tagged_answer_creation = AnswerCreationWithTag.format
}

class FormClient(val ws: WSClient, val credentials: Option[ApiCredential] = None) extends ResponseParser with WithWS with WithCredentials with EntityClient {

  import FormClient._

  def all(timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Seq[SimpleForm]]] = {
    ws.url(s"$endPoint/all", timeout).get().map(parse[Seq[SimpleForm]])
  }

  def byId(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Form]] = {
    ws.url(s"$endPoint/$id", timeout).get().map(parse[Form])
  }

  def byIdForUser(id: String, user_id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Form]] = {
    ws.url(s"$endPoint/$id/$user_id", timeout).get().map(parse[Form])
  }

  def byIdAndTagForUser(id: String, tag: String, user_id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Form]] = {
    ws.url(s"$endPoint/$id/$user_id/$tag", timeout).get().map(parse[Form])
  }

  def create(form_creation: FormCreation, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Form]] = {
    ws.url(s"$endPoint", timeout).put(Json.toJson(form_creation)).map(parse[Form])
  }

  def update(id: String, form_edition: FormEdition, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Form]] = {
    ws.url(s"$endPoint/$id").post(Json.toJson(form_edition)).map(parse[Form])
  }

  def lightUpdate(id: String, form_edition: LightFormEdition, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, SimpleForm]] = {
    ws.url(s"$endPoint/light/$id").post(Json.toJson(form_edition)).map(parse[SimpleForm])
  }

  def delete(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, SimpleForm]] = {
    ws.url(s"$endPoint/$id").delete().map(parse[SimpleForm])
  }

  def answer(user_id: String, answer_creations: Seq[AnswerCreation], timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Seq[Answer]]] = {
    ws.url(s"$endPoint/answer/$user_id").put(Json.toJson(answer_creations)).map(parse[Seq[Answer]])
  }

  def addTaggedAnswers(user_id: String, tagged_answer_creation: AnswerCreationWithTag, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Seq[Answer]]] = {
    ws.url(s"$endPoint/tagged-answer/$user_id").put(Json.toJson(tagged_answer_creation)).map(parse[Seq[Answer]])
  }
}
