package com.particeep.api

import com.particeep.api.core.{ApiClient, ApiCredential}

/**
 * This is a high level builder for api client. We may add new
 * Usage :
 *   val client = ParticeepApi.test("your_api_key", "your_api_secret")
 */
object ParticeepApi {

  private val last_version = "1"

  def test(key: String, secret: String): ApiClient = {
    new ApiClient("https://test-api.particeep.com", ApiCredential(key, secret), last_version) with InfoCapability with UserCapability with WalletCapability with KycCapability with RoleCapability with DocumentCapability with FormCapability
  }

  def prod(key: String, secret: String): ApiClient = {
    new ApiClient("https://test-api.particeep.com", ApiCredential(key, secret), last_version) with InfoCapability with UserCapability with WalletCapability with KycCapability with RoleCapability with DocumentCapability with FormCapability
  }
}
