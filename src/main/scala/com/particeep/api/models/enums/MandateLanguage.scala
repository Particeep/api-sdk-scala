package com.particeep.api.models.enums

object MandateLanguage {

  sealed abstract class MandateLanguage extends Enum

  case object fr extends MandateLanguage { val name: String = "fr" }
  case object en extends MandateLanguage { val name: String = "en" }
  case object de extends MandateLanguage { val name: String = "de" }
  case object es extends MandateLanguage { val name: String = "es" }

  object MandateLanguage extends EnumHelper[MandateLanguage] {
    def values: Set[MandateLanguage] = Set(fr, en, de, es)

    val defaultLocal = fr
  }
}
