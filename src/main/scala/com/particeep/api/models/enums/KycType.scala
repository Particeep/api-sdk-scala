package com.particeep.api.models.enums

object KycType {

  sealed abstract class KycType extends Enum

  case object ID_CARD extends KycType { val name: String = "ID_CARD" }
  case object ADDRESS_PROOF extends KycType { val name: String = "ADDRESS_PROOF" }
  case object COMPANY_STATUS extends KycType { val name: String = "COMPANY_STATUS" }
  case object RIB extends KycType { val name: String = "RIB" }
  case object KBIS extends KycType { val name: String = "KBIS" }
  case object TAX_STATUS extends KycType { val name: String = "TAX_STATUS" }
  case object PRESIDENT_OF_ASSOCIATION extends KycType { val name: String = "PRESIDENT_OF_ASSOCIATION" }
  case object OFFICIAL_JOURNAL extends KycType { val name: String = "OFFICIAL_JOURNAL" }
  case object ASSOCIATION_STATUS extends KycType { val name: String = "ASSOCIATION_STATUS" }

  object KycType extends EnumHelper[KycType] {
    def values: Set[KycType] = Set(ID_CARD, ADDRESS_PROOF, COMPANY_STATUS, RIB, KBIS, TAX_STATUS, PRESIDENT_OF_ASSOCIATION, OFFICIAL_JOURNAL, ASSOCIATION_STATUS)
  }

  def parseFromHipay(kycType: String): Option[KycType] = {
    kycType match {
      case "1" | "3" | "7" => Some(ID_CARD)
      case "2"             => Some(ADDRESS_PROOF)
      case "4" | "8"       => Some(KBIS)
      case "5"             => Some(COMPANY_STATUS)
      case "6"             => Some(RIB)
      case "9"             => Some(TAX_STATUS)
      case "11"            => Some(PRESIDENT_OF_ASSOCIATION)
      case "12"            => Some(OFFICIAL_JOURNAL)
      case "13"            => Some(ASSOCIATION_STATUS)
      case _               => None
    }
  }
}
