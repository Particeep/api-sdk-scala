package com.particeep.api

import com.particeep.api.core.{ ApiCredential, ResponseParser, WSClient }
import com.particeep.api.models.ErrorResult
import com.particeep.api.models.fundraise.equity.{ FundraiseEquity, FundraiseEquityCreation, FundraiseEquityEdition }
import play.api.libs.json.Json

import scala.concurrent.{ ExecutionContext, Future }

class FundraiseEquityClient(ws: WSClient) extends ResponseParser {

  private[this] val endPoint: String = "/equity"
  implicit val format = FundraiseEquity.format
  implicit val creation_format = FundraiseEquityCreation.format
  implicit val edition_format = FundraiseEquityEdition.format

  def byId(id: String, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, FundraiseEquity]] = {
    ws.url(s"$endPoint/fundraise/$id", timeout).get().map(parse[FundraiseEquity])
  }

  def byIds(ids: Seq[String], timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, List[FundraiseEquity]]] = {
    ws.url(s"$endPoint/fundraise", timeout)
      .withQueryString("ids" -> ids.mkString(","))
      .get()
      .map(parse[List[FundraiseEquity]])
  }

  def create(fundraise_equity_creation: FundraiseEquityCreation, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, FundraiseEquity]] = {
    ws.url(s"$endPoint/fundraise", timeout).put(Json.toJson(fundraise_equity_creation)).map(parse[FundraiseEquity])
  }

  def update(id: String, fundraise_equity_edition: FundraiseEquityEdition, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, FundraiseEquity]] = {
    ws.url(s"$endPoint/fundraise/$id", timeout).post(Json.toJson(fundraise_equity_edition)).map(parse[FundraiseEquity])
  }
}
