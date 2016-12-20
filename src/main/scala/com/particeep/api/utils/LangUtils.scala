package com.particeep.api.utils

import scala.language.postfixOps

object LangUtils {

  def productToQueryString(product: Product): List[(String, String)] = {
    val result = product.getClass.getDeclaredFields.map(_.getName) // all field names
      .zip(product.productIterator.to).toMap

    result
      .mapValues { v =>
        if (v.isInstanceOf[Option[_]]) {
          v.asInstanceOf[Option[_]].map(_.toString).getOrElse("")
        } else { v.toString }
      }
      .filter { case (k, v) => v != "" }
      .map { case (k, v) => (k, v) } toList
  }
}
