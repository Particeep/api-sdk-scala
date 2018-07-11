package com.particeep.api

import com.particeep.api.core._
import com.particeep.api.models.{ ErrorResult, PaginatedSequence, TableSearch }
import com.particeep.api.models.wallet._
import com.particeep.api.utils.LangUtils
import play.api.libs.json.Json

import scala.concurrent.{ ExecutionContext, Future }

trait WalletCapability {
  self: WSClient =>

  val wallet: WalletClient = new WalletClient(this)
  def wallet(credentials: ApiCredential): WalletClient = new WalletClient(this, Some(credentials))
}

object WalletClient {
  private val endPoint: String = "/wallet"
  private implicit val format = Wallet.format
  private implicit val creation_format = WalletCreation.format
  private implicit val cash_in_format = CashIn.format
  private implicit val cash_out_format = CashOut.format
  private implicit val transaction_format = TransactionWallet.format
  private implicit val transaction_data_format = TransactionWalletData.format
  private implicit val transfer_format = WalletTransfer.format
  private implicit val bank_account_format = BankAccount.format
  private implicit val bank_account_creation_format = BankAccountCreation.format
  private implicit val cashin_bank_account_format = CashInBankAccount.format
  private implicit val cashin_bank_account_creation_format = CashInBankAccountCreation.format
}

class WalletClient(val ws: WSClient, val credentials: Option[ApiCredential] = None) extends WithWS with WithCredentials with EntityClient {

  import WalletClient._

  def byId(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Wallet]] = {
    ws.get[Wallet](s"$endPoint/$id", timeout)
  }

  def byTargetIdAndType(owner_id: String, owner_type: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Wallet]] = {
    ws.get[Wallet](s"$endPoint/owner/$owner_id/$owner_type", timeout)
  }

  def consumerWallet(timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Wallet]] = {
    ws.get[Wallet](s"$endPoint/consumer", timeout)
  }

  def create(wallet_creation: WalletCreation, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Wallet]] = {
    ws.put[Wallet](s"$endPoint", timeout, Json.toJson(wallet_creation))
  }

  def cashin(id: String, cash_in: CashIn, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, String]] = {
    ws.post[String](s"$endPoint/$id/cashin", timeout, Json.toJson(cash_in))
  }

  def withdraw(id: String, cash_out: CashOut, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, TransactionWallet]] = {
    ws.post[TransactionWallet](s"$endPoint/$id/cashout", timeout, Json.toJson(cash_out))
  }

  def transfer(transfer: WalletTransfer, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, TransactionWallet]] = {
    ws.post[TransactionWallet](s"$endPoint/transfer", timeout, Json.toJson(transfer))
  }

  def allRelatedTransactions(id: String, criteria: TableSearch, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginatedSequence[TransactionWallet]]] = {
    ws.get[PaginatedSequence[TransactionWallet]](s"$endPoint/$id/transactions", timeout, LangUtils.productToQueryString(criteria))
  }

  def search(
    criteria:       TransactionWalletSearch,
    table_criteria: TableSearch,
    timeout:        Long                    = defaultTimeOut
  )(
    implicit
    exec: ExecutionContext
  ): Future[Either[ErrorResult, PaginatedSequence[TransactionWalletData]]] = {
    ws.get[PaginatedSequence[TransactionWalletData]](s"$endPoint/search", timeout, LangUtils.productToQueryString(criteria) ++ LangUtils.productToQueryString(table_criteria))
  }

  def addBankAccount(id: String, bank_account_creation: BankAccountCreation, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, BankAccount]] = {
    ws.put[BankAccount](s"$endPoint/$id/bankaccount", timeout, Json.toJson(bank_account_creation))
  }

  def getBankAccountsByWalletId(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Seq[BankAccount]]] = {
    ws.get[Seq[BankAccount]](s"$endPoint/$id/bankaccount", timeout)
  }

  def cashinBankAccount(
    id:                            String,
    cash_in_bank_account_creation: CashInBankAccountCreation,
    timeout:                       Long                      = defaultTimeOut
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, CashInBankAccount]] = {
    ws.post[CashInBankAccount](s"$endPoint/$id/cashin/bankAccount", timeout, Json.toJson(cash_in_bank_account_creation))
  }
}
