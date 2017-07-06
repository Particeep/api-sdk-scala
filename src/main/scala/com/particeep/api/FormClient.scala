package com.particeep.api

import com.particeep.api.core._
import com.particeep.api.models.ErrorResult
import com.particeep.api.models.form.creation._
import com.particeep.api.models.form.edition.{ FormEdition, PossibilityEdition, QuestionEdition, SectionEdition }
import com.particeep.api.models.form.edition_deep.FormEditionDeep
import com.particeep.api.models.form.get.{ Form, Possibility, Question, Section }
import com.particeep.api.models.form.get_deep._
import play.api.libs.json.Json

import scala.concurrent.{ ExecutionContext, Future }

trait FormCapability {
  self: WSClient =>

  val form: FormClient = new FormClient(this)

  def form(credentials: ApiCredential): FormClient = new FormClient(this, Some(credentials))
}

object FormClient {
  private val endPoint: String = "/form"
  private implicit val format = FormDeep.format

  private implicit val format_form_get = Form.format
  private implicit val format_form_creation = FormCreation.format
  private implicit val format_form_edition_deep = FormEditionDeep.format
  private implicit val format_edition_edition = FormEdition.format

  private implicit val format_section_get = Section.format
  private implicit val format_section_creation = SectionCreation.format
  private implicit val format_section_edition = SectionEdition.format

  private implicit val format_question_get = Question.format
  private implicit val format_question_creation = QuestionCreation.format
  private implicit val format_question_edition = QuestionEdition.format

  private implicit val format_possibility_get = Possibility.format
  private implicit val format_possibility_creation = PossibilityCreation.format
  private implicit val format_possibility_edition = PossibilityEdition.format

  private implicit val format_answer = AnswerDeep.format
  private implicit val format_answer_creation = AnswerCreation.format
  private implicit val format_tagged_answer_creation = AnswerCreationWithTag.format
}

class FormClient(val ws: WSClient, val credentials: Option[ApiCredential] = None) extends ResponseParser with WithWS with WithCredentials with EntityClient {

  import FormClient._

  def all(timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Seq[Form]]] = {
    ws.url(s"$endPoint/all", timeout).get().map(parse[Seq[Form]])
  }

  def byId(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FormDeep]] = {
    ws.url(s"$endPoint/$id", timeout).get().map(parse[FormDeep])
  }

  def byIdForUser(id: String, user_id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FormDeep]] = {
    ws.url(s"$endPoint/$id/$user_id", timeout).get().map(parse[FormDeep])
  }

  def byIdAndTagForUser(id: String, tag: String, user_id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FormDeep]] = {
    ws.url(s"$endPoint/$id/$user_id/$tag", timeout).get().map(parse[FormDeep])
  }

  def create(form_creation: FormCreation, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Form]] = {
    ws.url(s"$endPoint", timeout).put(Json.toJson(form_creation)).map(parse[Form])
  }

  def createSection(section_creation: SectionCreation, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Section]] = {
    ws.url(s"$endPoint/section", timeout).put(Json.toJson(section_creation)).map(parse[Section])
  }

  def createQuestion(question_creation: QuestionCreation, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Question]] = {
    ws.url(s"$endPoint/question", timeout).put(Json.toJson(question_creation)).map(parse[Question])
  }

  def createPossibility(possibility_creation: PossibilityCreation, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Possibility]] = {
    ws.url(s"$endPoint/possibility", timeout).put(Json.toJson(possibility_creation)).map(parse[Possibility])
  }

  def updateDeep(id: String, form_edition: FormEditionDeep, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FormDeep]] = {
    ws.url(s"$endPoint/$id").post(Json.toJson(form_edition)).map(parse[FormDeep])
  }

  def update(id: String, form_edition: FormEdition, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Form]] = {
    ws.url(s"$endPoint/light/$id").post(Json.toJson(form_edition)).map(parse[Form])
  }

  def udpateSection(id: String, section_edition: SectionEdition, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Section]] = {
    ws.url(s"$endPoint/section/$id").post(Json.toJson(section_edition)).map(parse[Section])
  }

  def udpateQuestion(id: String, question_edition: QuestionEdition, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Question]] = {
    ws.url(s"$endPoint/question/$id").post(Json.toJson(question_edition)).map(parse[Question])
  }

  def udpatePossibility(id: String, possibility_edition: PossibilityEdition, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Possibility]] = {
    ws.url(s"$endPoint/question/$id").post(Json.toJson(possibility_edition)).map(parse[Possibility])
  }

  def delete(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Form]] = {
    ws.url(s"$endPoint/$id").delete().map(parse[Form])
  }

  def deleteSection(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Section]] = {
    ws.url(s"$endPoint/section/$id").delete().map(parse[Section])
  }

  def deleteQuestion(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Question]] = {
    ws.url(s"$endPoint/question/$id").delete().map(parse[Question])
  }

  def deletePossibility(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Possibility]] = {
    ws.url(s"$endPoint/possibility/$id").delete().map(parse[Possibility])
  }

  def answer(user_id: String, answer_creations: Seq[AnswerCreation], timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Seq[AnswerDeep]]] = {
    ws.url(s"$endPoint/answer/$user_id").put(Json.toJson(answer_creations)).map(parse[Seq[AnswerDeep]])
  }

  def addTaggedAnswers(user_id: String, tagged_answer_creation: AnswerCreationWithTag, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Seq[AnswerDeep]]] = {
    ws.url(s"$endPoint/tagged-answer/$user_id").put(Json.toJson(tagged_answer_creation)).map(parse[Seq[AnswerDeep]])
  }
}
