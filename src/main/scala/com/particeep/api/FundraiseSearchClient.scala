package com.particeep.api

import com.particeep.api.core.{ResponseParser, WSClient}
import com.particeep.api.models.{ErrorResult, PaginatedSequence}
import com.particeep.api.models.fundraise.{FundraiseData, FundraiseSearch, NbProjectsByCategory}
import com.particeep.api.utils.LangUtils

import scala.concurrent.{ExecutionContext, Future}

trait FundraiseSearchCapability {
  self: WSClient =>

  val fundraise_search: FundraiseSearchClient = new FundraiseSearchClient(this)
}

class FundraiseSearchClient(ws: WSClient) extends ResponseParser {

  private val endPoint: String = "/fundraises"
  implicit val format = FundraiseData.format
  implicit val project_by_category_format = NbProjectsByCategory.format

  def search(criteria: FundraiseSearch, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginatedSequence[FundraiseData]]] = {
    ws.url(s"$endPoint/search", timeout)
      .withQueryString(LangUtils.productToQueryString(criteria): _*)
      .get
      .map(parse[PaginatedSequence[FundraiseData]])
  }

  def nbProjectsByActivityDomain(categories: List[String], timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[NbProjectsByCategory]]] = {
    ws.url(s"$endPoint/info/categories", timeout)
      .withQueryString("categories" -> categories.mkString(","))
      .get()
      .map(parse[List[NbProjectsByCategory]])
  }
}
