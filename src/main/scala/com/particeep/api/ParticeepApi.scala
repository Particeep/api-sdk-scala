package com.particeep.api

import com.particeep.api.core.{ ApiClient, ApiCredential, WSClient }

/**
 * This is a high level builder for api client. We may add new
 * Usage :
 *   val client = ParticeepApi.test("your_api_key", "your_api_secret")
 */
object ParticeepApi {

  private[this] val last_version = "1"

  trait AllCapability extends InfoCapability
      with UserCapability
      with WalletCapability
      with KycCapability
      with RoleCapability
      with DocumentCapability
      with FormCapability
      with DocumentGenerationCapability
      with SignatureCapability
      with EnterpriseCapability
      with FundraiseLoanCapability
      with FundraiseSearchCapability
      with NewsCapability
      with TransactionCapability
      with PaymentCapability
      with FundraiseEquityCapability
      with WalletSepaCapability {
    self: WSClient =>
  }

  def test(api_key: String, api_secret: String) = {
    new ApiClient(
      "https://test-api.particeep.com",
      last_version,
      Some(ApiCredential(api_key, api_secret))
    ) with AllCapability
  }

  def prod(api_key: String, api_secret: String) = {
    new ApiClient(
      "https://api.particeep.com",
      last_version,
      Some(ApiCredential(api_key, api_secret))
    ) with AllCapability
  }
}
