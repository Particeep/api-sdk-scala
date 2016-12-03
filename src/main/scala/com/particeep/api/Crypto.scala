package com.particeep.api

import java.util.Base64
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object Crypto {

  private val HEX_CHARS = "0123456789ABCDEF".toCharArray()

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
    e.encode(s.getBytes("UTF-8")).toString
  }

  def sign(toSign: Array[Byte], secret: Array[Byte]): Array[Byte] = {
    val mac: Mac = Mac.getInstance("HmacSHA1")
    val signingKey = new SecretKeySpec(secret, "HmacSHA1")
    mac.init(signingKey)
    mac.doFinal(toSign)
  }
}
