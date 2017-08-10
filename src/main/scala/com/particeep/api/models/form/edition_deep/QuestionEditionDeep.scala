package com.particeep.api.models.form.edition_deep

import com.particeep.api.models.enums.QuestionType.QuestionType
import com.particeep.api.models.form.edition_deep
import play.api.libs.json.Json

case class QuestionEditionDeep(
  id:                 Option[String],
  label:              Option[String],
  possibility_id_dep: Option[String]                                   = None,
  question_type:      Option[QuestionType],
  required:           Option[Boolean],
  index:              Option[Int],
  possibilities:      Option[Seq[edition_deep.PossibilityEditionDeep]]
)

object QuestionEditionDeep {
  implicit val possibility_edition_format = edition_deep.PossibilityEditionDeep.format
  val format = Json.format[QuestionEditionDeep]
}
