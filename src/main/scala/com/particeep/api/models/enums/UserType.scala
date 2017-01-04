package com.particeep.api.models.enums

object UserType {

  sealed abstract class UserType extends Enum

  case object NATURAL extends UserType { val name: String = "NATURAL" }
  case object LEGAL extends UserType { val name: String = "LEGAL" }
  case object SELF_EMPLOYED extends UserType { val name: String = "SELF_EMPLOYED" }
  case object ASSOCIATION extends UserType { val name: String = "ASSOCIATION" }

  object UserType extends EnumHelper[UserType] {
    def values: Set[UserType] = Set(NATURAL, LEGAL, ASSOCIATION, SELF_EMPLOYED)
  }

}

