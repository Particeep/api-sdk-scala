package com.particeep.api

import java.io.File

import com.particeep.api.core._
import com.particeep.api.models.{ ErrorResult, PaginatedSequence, TableSearch }
import com.particeep.api.models.form.creation._
import com.particeep.api.models.form.edition.{ FormEdition, PossibilityEdition, QuestionEdition, SectionEdition }
import com.particeep.api.models.form.edition_deep.FormEditionDeep
import com.particeep.api.models.form.get._
import com.particeep.api.models.form.get_deep._
import com.particeep.api.models.imports.ImportResult
import com.particeep.api.utils.LangUtils
import play.api.libs.json.Json

import scala.concurrent.{ ExecutionContext, Future }

trait FormCapability {
  self: WSClient =>

  val form: FormClient = new FormClient(this)

  def form(credentials: ApiCredential): FormClient = new FormClient(this, Some(credentials))
}

object FormClient {
  private val endPoint: String = "/form"
  private val endPoint_import: String = "/import"
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

  private implicit val format_answer = Answer.format
  private implicit val format_answer_creation = AnswerCreation.format
  private implicit val format_tagged_answer_creation = AnswerCreationWithTag.format

  private implicit val importResultReads = ImportResult.format[Seq[Answer]]
}

class FormClient(val ws: WSClient, val credentials: Option[ApiCredential] = None) extends WithWS with WithCredentials with EntityClient {

  import FormClient._

  def all(timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Seq[Form]]] = {
    ws.get[Seq[Form]](s"$endPoint/all", timeout)
  }

  def byId(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FormDeep]] = {
    ws.get[FormDeep](s"$endPoint/$id", timeout)
  }

  def byIdForUser(id: String, user_id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FormDeep]] = {
    ws.get[FormDeep](s"$endPoint/$id/$user_id", timeout)
  }

  def byIdAndTagForUser(id: String, tag: String, user_id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FormDeep]] = {
    ws.get[FormDeep](s"$endPoint/$id/$user_id/$tag", timeout)
  }

  def create(form_creation: FormCreation, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Form]] = {
    ws.put[Form](s"$endPoint", timeout, Json.toJson(form_creation))
  }

  def createSection(section_creation: SectionCreation, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Section]] = {
    ws.put[Section](s"$endPoint/section", timeout, Json.toJson(section_creation))
  }

  def createQuestion(question_creation: QuestionCreation, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Question]] = {
    ws.put[Question](s"$endPoint/question", timeout, Json.toJson(question_creation))
  }

  def createPossibility(possibility_creation: PossibilityCreation, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Possibility]] = {
    ws.put[Possibility](s"$endPoint/possibility", timeout, Json.toJson(possibility_creation))
  }

  def updateDeep(id: String, form_edition: FormEditionDeep, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FormDeep]] = {
    ws.post[FormDeep](s"$endPoint/$id", timeout, Json.toJson(form_edition))
  }

  def update(id: String, form_edition: FormEdition, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Form]] = {
    ws.post[Form](s"$endPoint/light/$id", timeout, Json.toJson(form_edition))
  }

  def udpateSection(id: String, section_edition: SectionEdition, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Section]] = {
    ws.post[Section](s"$endPoint/section/$id", timeout, Json.toJson(section_edition))
  }

  def udpateQuestion(id: String, question_edition: QuestionEdition, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Question]] = {
    ws.post[Question](s"$endPoint/question/$id", timeout, Json.toJson(question_edition))
  }

  def udpatePossibility(id: String, possibility_edition: PossibilityEdition, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Possibility]] = {
    ws.post[Possibility](s"$endPoint/possibility/$id", timeout, Json.toJson(possibility_edition))
  }

  def delete(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Form]] = {
    ws.delete[Form](s"$endPoint/$id", timeout)
  }

  def deleteSection(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Section]] = {
    ws.delete[Section](s"$endPoint/section/$id", timeout)
  }

  def deleteQuestion(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Question]] = {
    ws.delete[Question](s"$endPoint/question/$id", timeout)
  }

  def deletePossibility(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Possibility]] = {
    ws.delete[Possibility](s"$endPoint/possibility/$id", timeout)
  }

  def answer(user_id: String, answer_creations: Seq[AnswerCreation], timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Seq[Answer]]] = {
    ws.put[Seq[Answer]](s"$endPoint/answer/$user_id", timeout, Json.toJson(answer_creations))
  }

  def addTaggedAnswers(user_id: String, tagged_answer_creation: AnswerCreationWithTag, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Seq[Answer]]] = {
    ws.put[Seq[Answer]](s"$endPoint/tagged-answer/$user_id", timeout, Json.toJson(tagged_answer_creation))
  }

  def importAnswersFromCsv(csv: File, timeout: Long = defaultImportTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, ImportResult[Seq[Answer]]]] = {
    ws.postFile[ImportResult[Seq[Answer]]](s"$endPoint_import/form/answer", timeout, csv, "text/csv", List())
  }

  def search(
    criteria:       FormsSearch,
    table_criteria: TableSearch,
    timeout:        Long        = defaultTimeOut
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginatedSequence[Form]]] = {
    ws.get[PaginatedSequence[Form]](s"$endPoint/search", timeout, LangUtils.productToQueryString(criteria) ++ LangUtils.productToQueryString(table_criteria))
  }
}
