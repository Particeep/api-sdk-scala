package com.particeep.api

import com.particeep.api.core.{ ApiClient, ApiCredential }
import play.api.libs.ws.ahc.StandaloneAhcWSClient

/**
 * This is a high level builder for api client. We may add new
 * Usage :
 *   val client = ParticeepApi.test("your_api_key", "your_api_secret")
 */
object ParticeepApi {

  private[this] val last_version = "1"

  def test(api_key: String, api_secret: String)(implicit materializer: akka.stream.Materializer) = {
    new ApiClient(StandaloneAhcWSClient(), "https://test-api.particeep.com", last_version, Some(ApiCredential(api_key, api_secret))) with InfoCapability with UserCapability with WalletCapability with KycCapability with RoleCapability with DocumentCapability with FormCapability with DocumentGenerationCapability with SignatureCapability with EnterpriseCapability with FundraiseLoanCapability with FundraiseSearchCapability with NewsCapability with TransactionCapability with PaymentCapability with FundraiseEquityCapability with WalletSepaCapability
  }

  def prod(api_key: String, api_secret: String)(implicit materializer: akka.stream.Materializer) = {
    new ApiClient(StandaloneAhcWSClient(), "https://api.particeep.com", last_version, Some(ApiCredential(api_key, api_secret))) with InfoCapability with UserCapability with WalletCapability with KycCapability with RoleCapability with DocumentCapability with FormCapability with DocumentGenerationCapability with SignatureCapability with EnterpriseCapability with FundraiseLoanCapability with FundraiseSearchCapability with NewsCapability with TransactionCapability with PaymentCapability with FundraiseEquityCapability with WalletSepaCapability
  }
}
