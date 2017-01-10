package com.particeep.api.models.enums

import scala.language.implicitConversions

object QuestionType {

  sealed abstract class QuestionType extends Enum
  case object TEXT extends QuestionType { val name: String = "TEXT" }
  case object LONGTEXT extends QuestionType { val name: String = "LONGTEXT" }
  case object RADIO extends QuestionType { val name: String = "RADIO" }
  case object SELECT extends QuestionType { val name: String = "SELECT" }
  case object CHECKBOX extends QuestionType { val name: String = "CHECKBOX" }

  object QuestionType extends EnumHelper[QuestionType] {
    def values: Set[QuestionType] = Set(TEXT, LONGTEXT, RADIO, SELECT, CHECKBOX)

    implicit def stringToQuestionType(value: String): QuestionType = get(value).getOrElse(TEXT)

    def isFreeType(question_type: QuestionType): Boolean = {
      Seq(TEXT, LONGTEXT).contains(question_type)
    }
  }
}
