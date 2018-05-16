package com.particeep.api

import play.api.libs.json._

import scala.concurrent.{ ExecutionContext, Future }
import com.particeep.api.models._
import com.particeep.api.models.imports.ImportResult
import com.particeep.api.utils.LangUtils
import com.particeep.api.models.user._
import play.api.libs.Files.TemporaryFile
import play.api.mvc.MultipartFormData
import com.particeep.api.core._

trait UserCapability {
  self: WSClient =>

  val user: UserClient = new UserClient(this)

  def user(credentials: ApiCredential): UserClient = new UserClient(this, Some(credentials))
}

object UserClient {
  private val endPoint: String = "/user"
  private val endPoint_import: String = "/import"
  private implicit val format = User.format
  private implicit val creation_format = UserCreation.format
  private implicit val edition_format = UserEdition.format
  private implicit val data_format = UserData.format
  private implicit val importResultReads = ImportResult.format[User]

  private case class ChangePassword(old_password: Option[String], new_password: String)
  private implicit val change_password_format = Json.format[ChangePassword]
}

class UserClient(val ws: WSClient, val credentials: Option[ApiCredential] = None) extends WithWS with WithCredentials with EntityClient {

  import UserClient._

  def byId(id: String, timeout: Long = defaultTimeOutInSeconds)(implicit exec: ExecutionContext): Future[Either[ErrorResult, User]] = {
    ws.get[User](s"$endPoint/$id", timeout)
  }

  def byIds(ids: Seq[String], timeout: Long = defaultTimeOutInSeconds)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[User]]] = {
    ws.get[List[User]](s"$endPoint", timeout, List("ids" -> ids.mkString(",")))
  }

  def byEmail(email: String, timeout: Long = defaultTimeOutInSeconds)(implicit exec: ExecutionContext): Future[Either[ErrorResult, User]] = {
    ws.get[User](s"$endPoint/email/$email", timeout)
  }

  def searchByName(name: String, timeout: Long = defaultTimeOutInSeconds)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[User]]] = {
    ws.get[List[User]](s"$endPoint/name/$name", timeout)
  }

  def search(criteria: UserSearch, table_criteria: TableSearch, timeout: Long = defaultTimeOutInSeconds)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginatedSequence[UserData]]] = {
    ws.get[PaginatedSequence[UserData]](s"$endPoint/search", timeout, LangUtils.productToQueryString(criteria) ++ LangUtils.productToQueryString(table_criteria))
  }

  def create(user_creation: UserCreation, timeout: Long = defaultTimeOutInSeconds)(implicit exec: ExecutionContext): Future[Either[ErrorResult, User]] = {
    ws.put[User](s"$endPoint", timeout, Json.toJson(user_creation))
  }

  def update(id: String, user_edition: UserEdition, timeout: Long = defaultTimeOutInSeconds)(implicit exec: ExecutionContext): Future[Either[ErrorResult, User]] = {
    ws.post[User](s"$endPoint/$id", timeout, Json.toJson(user_edition))
  }

  def authenticate(email: String, password: String, timeout: Long = defaultTimeOutInSeconds)(implicit exec: ExecutionContext): Future[Either[ErrorResult, User]] = {
    ws.post[User](s"$endPoint/authenticate", timeout, Json.toJson(Map("email" -> email, "password" -> password)))
  }

  def getOrCreate(user_creation: UserCreation, timeout: Long = defaultTimeOutInSeconds)(implicit exec: ExecutionContext): Future[Either[ErrorResult, User]] = {
    ws.post[User](s"$endPoint/getOrCreate", timeout, Json.toJson(user_creation))
  }

  def changePassword(id: String, old_password: Option[String], new_password: String, timeout: Long = defaultTimeOutInSeconds)(implicit exec: ExecutionContext): Future[Either[ErrorResult, User]] = {
    ws.post[User](s"$endPoint/$id/changePassword", timeout, Json.toJson(ChangePassword(old_password, new_password)))
  }

  def verifyAccount(id: String, timeout: Long = defaultTimeOutInSeconds)(implicit exec: ExecutionContext): Future[Either[ErrorResult, User]] = {
    ws.post[User](s"$endPoint/verify/$id", timeout, Json.toJson(""))
  }

  def delete(id: String, timeout: Long = defaultTimeOutInSeconds)(implicit exec: ExecutionContext): Future[Either[ErrorResult, User]] = {
    ws.delete[User](s"$endPoint/$id", timeout)
  }

  def importFromCsv(csv: MultipartFormData[TemporaryFile], timeout: Long = defaultImportTimeOutInSeconds)(implicit exec: ExecutionContext): Future[Either[ErrorResult, ImportResult[User]]] = {
    ws.postFile[ImportResult[User]](s"$endPoint_import/user/csv", timeout, csv, List())
  }

}
