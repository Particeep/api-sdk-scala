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

class WalletSepaClient(val ws: WSClient, val credentials: Option[ApiCredential] = None) extends WithWS with WithCredentials with EntityClient {

  import WalletSepaClient._

  def sddMandates(wallet_id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Seq[SddMandate]]] = {
    ws.get[Seq[SddMandate]](s"$endPoint/$wallet_id/sepa", timeout)
  }

  def addSddMandate(wallet_id: String, sdd_mandate_creation: SddMandateCreation, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, SddMandate]] = {
    ws.put[SddMandate](s"$endPoint/$wallet_id/sepa", timeout, Json.toJson(sdd_mandate_creation))
  }

  private[this] case class OwnerIp(owner_ip: String)

  private[this] implicit lazy val owner_ip_format = Json.format[OwnerIp]

  def removeSddMandate(sdd_mandate_id: String, owner_ip: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, SddMandate]] = {
    ws.delete[SddMandate](s"$endPoint/sepa/$sdd_mandate_id", timeout, Json.toJson(OwnerIp(owner_ip)))
  }

  def signSddMandate(sdd_mandate_id: String, sepa_sdd_signature: SepaSddSignature, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, String]] = {
    ws.post[String](s"$endPoint/sepa/$sdd_mandate_id/sign", timeout, Json.toJson(sepa_sdd_signature))
  }

  def cashInSddMandate(sdd_mandate_id: String, sdd_cashin: SddCashIn, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, TransactionWallet]] = {
    ws.post[TransactionWallet](s"$endPoint/sepa/$sdd_mandate_id/cashin", timeout, Json.toJson(sdd_cashin))
  }
}
