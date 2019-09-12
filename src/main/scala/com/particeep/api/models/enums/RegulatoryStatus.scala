package com.particeep.api.models.enums

object RegulatoryStatus {
  sealed abstract class RegulatoryStatus extends Enum

  case object CIF extends RegulatoryStatus { val name: String = "CIF" }
  case object ALPSI extends RegulatoryStatus { val name: String = "ALPSI" }
  case object PSI extends RegulatoryStatus { val name: String = "PSI" }
  case object SDG extends RegulatoryStatus { val name: String = "SDG" }

  object RegulatoryStatus extends EnumHelper[RegulatoryStatus] {
    def values: Set[RegulatoryStatus] = Set(CIF, ALPSI, PSI, SDG)
  }
}
