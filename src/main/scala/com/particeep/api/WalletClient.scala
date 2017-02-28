package com.particeep.api

import com.particeep.api.core.{ ApiCredential, ResponseParser, WSClient }
import com.particeep.api.models.{ ErrorResult, PaginatedSequence, TableSearch }
import com.particeep.api.models.wallet._
import com.particeep.api.utils.LangUtils
import play.api.libs.json.Json

import scala.concurrent.{ ExecutionContext, Future }

trait WalletCapability {
  self: WSClient =>

  val wallet: WalletClient = new WalletClient(this)
}

class WalletClient(ws: WSClient) extends ResponseParser {

  private[this] val endPoint: String = "/wallet"
  implicit val format = Wallet.format
  implicit val creation_format = WalletCreation.format
  implicit val cash_in_format = CashIn.format
  implicit val cash_out_format = CashOut.format
  implicit val transaction_format = TransactionWallet.format
  implicit val transfer_format = WalletTransfer.format
  implicit val bank_account_format = BankAccount.format
  implicit val bank_account_creation_format = BankAccountCreation.format
  implicit val cashin_bank_account_format = CashInBankAccount.format
  implicit val cashin_bank_account_creation_format = CashInBankAccountCreation.format

  def byId(id: String, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, Wallet]] = {
    ws.url(s"$endPoint/$id", timeout).get().map(parse[Wallet])
  }

  def byTargetIdAndType(owner_id: String, owner_type: String, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, Wallet]] = {
    ws.url(s"$endPoint/owner/$owner_id/$owner_type", timeout).get().map(parse[Wallet])
  }

  def consumerWallet(timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, Wallet]] = {
    ws.url(s"$endPoint/consumer", timeout).get().map(parse[Wallet])
  }

  def create(wallet_creation: WalletCreation, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, Wallet]] = {
    ws.url(s"$endPoint", timeout).put(Json.toJson(wallet_creation)).map(parse[Wallet])
  }

  def cashin(id: String, cash_in: CashIn, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, String]] = {
    ws.url(s"$endPoint/$id/cashin", timeout).post(Json.toJson(cash_in)).map(parse[String])
  }

  def withdraw(id: String, cash_out: CashOut, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, TransactionWallet]] = {
    ws.url(s"$endPoint/$id/cashout", timeout).post(Json.toJson(cash_out)).map(parse[TransactionWallet])
  }

  def transfer(transfer: WalletTransfer, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, TransactionWallet]] = {
    ws.url(s"$endPoint/transfer", timeout).post(Json.toJson(transfer)).map(parse[TransactionWallet])
  }

  def allRelatedTransactions(id: String, criteria: TableSearch, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, PaginatedSequence[TransactionWallet]]] = {
    ws.url(s"$endPoint/transactions/$id", timeout)
      .withQueryString(LangUtils.productToQueryString(criteria): _*)
      .get
      .map(parse[PaginatedSequence[TransactionWallet]])
  }

  def addBankAccount(id: String, bank_account_creation: BankAccountCreation, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, BankAccount]] = {
    ws.url(s"$endPoint/$id/bankaccount", timeout).put(Json.toJson(bank_account_creation)).map(parse[BankAccount])
  }

  def getBankAccountsByWalletId(id: String, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, Seq[BankAccount]]] = {
    ws.url(s"$endPoint/$id/bankaccount", timeout).get.map(parse[Seq[BankAccount]])
  }

  def cashinBankAccount(id: String, cash_in_bank_account_creation: CashInBankAccountCreation, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, CashInBankAccount]] = {
    ws.url(s"$endPoint/$id/cashin/bankAccount", timeout).post(Json.toJson(cash_in_bank_account_creation)).map(parse[CashInBankAccount])
  }
}
