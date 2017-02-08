package com.particeep.api.core

import java.util.Base64
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object Crypto {

  private[this] val HEX_CHARS = "0123456789ABCDEF".toCharArray()
  private[this] final val UTF_8 = "UTF-8"
  private[this] final val HMAC_SHA1 = "HmacSHA1"

  def encodeToHex(toEncode: Array[Byte]): Array[Char] = {
    val len = toEncode.length
    val hexChars = new Array[Char](len * 2)

    var charIndex: Int = 0
    var startIndex: Int = 0
    while (charIndex < hexChars.length) {
      val bite: Int = toEncode(startIndex) & 0xff
      startIndex = startIndex + 1
      hexChars(charIndex) = HEX_CHARS(bite >> 4)
      charIndex = charIndex + 1
      hexChars(charIndex) = HEX_CHARS(bite & 0xf)
      charIndex = charIndex + 1
    }

    hexChars
  }

  def encodeBase64(s: String): String = {
    val e = Base64.getEncoder()
    val b_array = e.encode(s.getBytes(UTF_8))
    new String(b_array, UTF_8)
  }

  def sign(toSign: Array[Byte], secret: Array[Byte]): Array[Byte] = {
    val mac: Mac = Mac.getInstance(HMAC_SHA1)
    val signingKey = new SecretKeySpec(secret, HMAC_SHA1)
    mac.init(signingKey)
    mac.doFinal(toSign)
  }
}
