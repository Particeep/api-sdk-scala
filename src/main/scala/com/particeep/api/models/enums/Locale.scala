package com.particeep.api.models.enums

object Locale {

  sealed abstract class Locale extends Enum

  case object fr_FR extends Locale { val name: String = "fr_FR" }
  case object en_GB extends Locale { val name: String = "en_GB" }
  case object en_US extends Locale { val name: String = "en_US" }
  case object es_ES extends Locale { val name: String = "es_ES" }
  case object de_DE extends Locale { val name: String = "de_DE" }
  case object pt_PT extends Locale { val name: String = "pt_PT" }
  case object pt_BR extends Locale { val name: String = "pt_BR" }
  case object nl_NL extends Locale { val name: String = "nl_NL" }
  case object nl_BE extends Locale { val name: String = "nl_BE" }

  object Locale extends EnumHelper[Locale] {
    def values: Set[Locale] = Set(fr_FR, en_GB, en_US, es_ES, de_DE, pt_PT, pt_BR, nl_NL, nl_BE)

    val defaultLocal = fr_FR

    def getLocaleOrDefault(localeSOpt: Option[String]): Locale = {
      val localeOpt = get(localeSOpt)
      localeOpt.getOrElse(defaultLocal)
    }
  }
}
