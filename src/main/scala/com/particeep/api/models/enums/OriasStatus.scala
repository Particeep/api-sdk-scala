package com.particeep.api.models.enums

object OriasStatus {
  sealed abstract class OriasStatus extends Enum

  case object REGISTERED extends OriasStatus { val name: String = "INSCRIT" }
  case object DELETED extends OriasStatus { val name: String = "SUPPRIME" }
  case object NEVER_REGISTERED extends OriasStatus { val name: String = "JAMAIS_INSCRIT" }

  object OriasStatus extends EnumHelper[OriasStatus] {
    def values: Set[OriasStatus] = Set(REGISTERED, DELETED, NEVER_REGISTERED)
  }
}
