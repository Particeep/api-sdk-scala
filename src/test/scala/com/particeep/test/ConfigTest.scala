package com.particeep.test

import com.particeep.api.ApiCredential

object ConfigTest {

  val baseUrl = "https://test-api.particeep.com"
  val credential = ApiCredential(
    apiKey = "your_api_key",
    apiSecret = "your_api_secret"
  )
  val version = "1"
}
