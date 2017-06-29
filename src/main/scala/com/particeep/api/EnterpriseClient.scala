package com.particeep.api

import com.particeep.api.core._
import com.particeep.api.models.{ ErrorResult, PaginatedSequence }
import com.particeep.api.models.enterprise._
import com.particeep.api.models.imports.ImportResult
import com.particeep.api.utils.LangUtils
import play.api.libs.Files.TemporaryFile
import play.api.libs.json.Json
import play.api.mvc.MultipartFormData

import scala.concurrent.{ ExecutionContext, Future }

trait EnterpriseCapability {
  self: WSClient =>

  val enterprise: EnterpriseClient = new EnterpriseClient(this)
  def enterprise(credentials: ApiCredential): EnterpriseClient = new EnterpriseClient(this, Some(credentials))
}

object EnterpriseClient {
  private val endPoint: String = "/enterprise"
  private val endPoint_import: String = "/import"
  private implicit val format = Enterprise.format
  private implicit val creation_format = EnterpriseCreation.format
  private implicit val edition_format = EnterpriseEdition.format
  private implicit val manager_link_format = ManagerLink.format
  private implicit val manager_creation_format = ManagerCreation.format
  private implicit val nb_enterprises_by_activity_domain_format = NbEnterprisesByActivityDomain.format
  private implicit val importResultFormat = ImportResult.format

}

class EnterpriseClient(val ws: WSClient, val credentials: Option[ApiCredential] = None) extends ResponseParser with WithWS with WithCredentials with EntityClient {

  import EnterpriseClient._

  def byId(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Enterprise]] = {
    ws.url(s"$endPoint/$id", timeout).get().map(parse[Enterprise])
  }

  def byIds(ids: List[String], timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[Enterprise]]] = {
    ws.url(s"$endPoint", timeout)
      .withQueryString("ids" -> ids.mkString(","))
      .get()
      .map(parse[List[Enterprise]])
  }

  def byUserId(user_id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[Enterprise]]] = {
    ws.url(s"$endPoint/user/$user_id", timeout).get().map(parse[List[Enterprise]])
  }

  def create(enterprise_creation: EnterpriseCreation, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Enterprise]] = {
    ws.url(s"$endPoint", timeout).put(Json.toJson(enterprise_creation)).map(parse[Enterprise])
  }

  def update(id: String, enterprise_edition: EnterpriseEdition, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Enterprise]] = {
    ws.url(s"$endPoint/$id", timeout).post(Json.toJson(enterprise_edition)).map(parse[Enterprise])
  }

  def search(criteria: EnterpriseSearch, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginatedSequence[Enterprise]]] = {
    ws.url(s"$endPoint/search", timeout)
      .withQueryString(LangUtils.productToQueryString(criteria): _*)
      .get
      .map(parse[PaginatedSequence[Enterprise]])
  }

  def delete(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Enterprise]] = {
    ws.url(s"$endPoint/$id", timeout).delete().map(parse[Enterprise])
  }

  def addManager(id: String, manager_creation: ManagerCreation, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, ManagerLink]] = {
    ws.url(s"$endPoint/$id/manager", timeout).put(Json.toJson(manager_creation)).map(parse[ManagerLink])
  }

  def getManagers(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[ManagerLink]]] = {
    ws.url(s"$endPoint/$id/manager", timeout).get().map(parse[List[ManagerLink]])
  }

  def deleteManager(id: String, manager_id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, ManagerLink]] = {
    ws.url(s"$endPoint/$id/manager/$manager_id", timeout).delete().map(parse[ManagerLink])
  }

  def nbEnterprisesByActivityDomain(timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Seq[NbEnterprisesByActivityDomain]]] = {
    ws.url(s"$endPoint/info/activity/domain", timeout).get().map(parse[Seq[NbEnterprisesByActivityDomain]])
  }

  def importFromCsv(csv: MultipartFormData[TemporaryFile], timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, ImportResult]] = {
    ws.postFile(s"$endPoint_import/enterprise/csv", csv, List(), timeout).map(parse[ImportResult])
  }
}
