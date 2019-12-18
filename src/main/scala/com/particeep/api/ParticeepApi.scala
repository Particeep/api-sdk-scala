package com.particeep.api

import akka.actor.ActorSystem
import akka.stream.Materializer
import com.particeep.api.core.{ ApiClient, ApiCredential, WSClient }

/**
 * This is a high level builder for api client. We may add new
 * Usage :
 *   val client = ParticeepApi.test("your_api_key", "your_api_secret")
 */
object ParticeepApi {

  private[this] val last_version = "1"

  trait AllCapability
    extends ClubDealCapability
    with DocumentCapability
    with DocumentGenerationCapability
    with EnterpriseCapability
    with FinancialProductCapability
    with FormCapability
    with FundraiseEquityCapability
    with FundraiseLoanCapability
    with FundraiseRewardCapability
    with FundraiseSearchCapability
    with InfoCapability
    with KpiCapability
    with KycCapability
    with NewsCapability
    with PartnerCapability
    with PaymentCapability
    with RoleCapability
    with ScoringMetricsCapability
    with SignatureCapability
    with TransactionCapability
    with UserCapability
    with WalletCapability
    with WalletSepaCapability
    with WebHookCapability
    with PhoneMessagingCapability {
    self: WSClient =>
  }

  def test(api_key: String, api_secret: String)(implicit s: ActorSystem, m: Materializer) = {
    new ApiClient(
      "https://test-api.particeep.com",
      last_version,
      Some(ApiCredential(api_key, api_secret))
    ) with AllCapability
  }

  def prod(api_key: String, api_secret: String)(implicit s: ActorSystem, m: Materializer) = {
    new ApiClient(
      "https://api.particeep.com",
      last_version,
      Some(ApiCredential(api_key, api_secret))
    ) with AllCapability
  }
}
