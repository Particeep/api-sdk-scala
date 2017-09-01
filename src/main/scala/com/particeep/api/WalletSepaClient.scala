package com.particeep.api

import com.particeep.api.core._
import com.particeep.api.models.ErrorResult
import com.particeep.api.models.wallet.TransactionWallet
import com.particeep.api.models.wallet.sepa.{ SddCashIn, SddMandate, SddMandateCreation, SepaSddSignature }
import play.api.libs.json.Json

import scala.concurrent.{ ExecutionContext, Future }

trait WalletSepaCapability {
  self: WSClient =>

  val wallet_sepa: WalletSepaClient = new WalletSepaClient(this)
  def walletSepa(credentials: ApiCredential): WalletSepaClient = new WalletSepaClient(this, Some(credentials))
}

object WalletSepaClient {
  private val endPoint: String = "/wallet"
  private implicit val format = SddMandate.format
  private implicit val creation_format = SddMandateCreation.format
  private implicit val signature_format = SepaSddSignature.format
  private implicit val cashin_format = SddCashIn.format
  private implicit val transaction_wallet_format = TransactionWallet.format
}

class WalletSepaClient(val ws: WSClient, val credentials: Option[ApiCredential] = None) extends ResponseParser with WithWS with WithCredentials with EntityClient {

  import WalletSepaClient._

  def sddMandates(wallet_id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Seq[SddMandate]]] = {
    ws.url(s"$endPoint/$wallet_id/sepa", timeout).get().map(parse[Seq[SddMandate]])
  }

  def addSddMandate(wallet_id: String, sdd_mandate_creation: SddMandateCreation, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, SddMandate]] = {
    ws.url(s"$endPoint/$wallet_id/sepa", timeout).put(Json.toJson(sdd_mandate_creation)).map(parse[SddMandate])
  }

  private[this] case class OwnerIp(owner_ip: String)

  private[this] implicit lazy val owner_ip_format = Json.format[OwnerIp]

  def removeSddMandate(sdd_mandate_id: String, owner_ip: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, SddMandate]] = {
    val body = Json.toJson(OwnerIp(owner_ip))
    ws.url(s"$endPoint/sepa/$sdd_mandate_id", timeout).withMethod("DELETE").withBody(body).execute().map(parse[SddMandate])
  }

  def signSddMandate(sdd_mandate_id: String, sepa_sdd_signature: SepaSddSignature, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, String]] = {
    ws.url(s"$endPoint/sepa/$sdd_mandate_id/sign", timeout).post(Json.toJson(sepa_sdd_signature)).map(parse[String])
  }

  def cashInSddMandate(sdd_mandate_id: String, sdd_cashin: SddCashIn, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, TransactionWallet]] = {
    ws.url(s"$endPoint/sepa/$sdd_mandate_id/cashin", timeout).post(Json.toJson(sdd_cashin)).map(parse[TransactionWallet])
  }
}
