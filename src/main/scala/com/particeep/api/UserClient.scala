package com.particeep.api

import com.particeep.api.core.{ ApiCredential, ResponseParser, WSClient }
import play.api.libs.json._

import scala.concurrent.{ ExecutionContext, Future }
import com.particeep.api.models._
import com.particeep.api.models.imports.ImportResult
import com.particeep.api.utils.LangUtils
import com.particeep.api.models.user._
import play.api.libs.Files.TemporaryFile
import play.api.mvc.{ MultipartFormData, Results }

trait UserCapability {
  self: WSClient =>

  val user: UserClient = new UserClient(this)
}

class UserClient(ws: WSClient) extends ResponseParser {

  private[this] val endPoint: String = "/user"
  private[this] val endPoint_import: String = "/import"
  implicit val format = User.format
  implicit val creation_format = UserCreation.format
  implicit val edition_format = UserEdition.format
  implicit val data_format = UserData.format
  implicit val importResultFormat = ImportResult.format

  def byId(id: String, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, User]] = {
    ws.url(s"$endPoint/$id", timeout).get().map(parse[User])
  }

  def byIds(ids: Seq[String], timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, List[User]]] = {
    ws.url(s"$endPoint", timeout)
      .withQueryString("ids" -> ids.mkString(","))
      .get()
      .map(parse[List[User]])
  }

  def byEmail(email: String, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, User]] = {
    ws.url(s"$endPoint/email/$email", timeout).get().map(parse[User])
  }

  def searchByName(name: String, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, List[User]]] = {
    ws.url(s"$endPoint/name/$name", timeout).get.map(parse[List[User]])
  }

  def search(criteria: UserSearch, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, PaginatedSequence[UserData]]] = {
    ws.url(s"$endPoint/search", timeout)
      .withQueryString(LangUtils.productToQueryString(criteria): _*)
      .get
      .map(parse[PaginatedSequence[UserData]])
  }

  def create(user_creation: UserCreation, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, User]] = {
    ws.url(s"$endPoint", timeout).put(Json.toJson(user_creation)).map(parse[User])
  }

  def update(id: String, user_edition: UserEdition, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, User]] = {
    ws.url(s"$endPoint/$id", timeout).post(Json.toJson(user_edition)).map(parse[User])
  }

  def authenticate(email: String, password: String, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, User]] = {
    val authenticateJson: JsValue = Json.toJson(Map("email" -> email, "password" -> password))
    ws.url(s"$endPoint/authenticate", timeout)
      .post(authenticateJson)
      .map(parse[User])
  }

  def getOrCreate(user_creation: UserCreation, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, User]] = {
    ws.url(s"$endPoint/getOrCreate", timeout).post(Json.toJson(user_creation)).map(parse[User])
  }

  private[this] case class ChangePassword(old_password: Option[String], new_password: String)

  private[this] implicit val change_password_format = Json.format[ChangePassword]

  def changePassword(id: String, old_password: Option[String], new_password: String, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, User]] = {
    ws.url(s"$endPoint/$id/changePassword", timeout).post(Json.toJson(ChangePassword(old_password, new_password))).map(parse[User])
  }

  def verifyAccount(id: String, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, User]] = {
    ws.url(s"$endPoint/verify/$id", timeout).post(Results.EmptyContent()).map(parse[User])
  }

  def delete(id: String, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, User]] = {
    ws.url(s"$endPoint/$id", timeout).delete().map(parse[User])
  }

  def importFromCsv(csv: MultipartFormData[TemporaryFile], timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, ImportResult]] = {
    ws.postFile(s"$endPoint_import/user/csv", csv, List(), timeout).map(parse[ImportResult])
  }

}
