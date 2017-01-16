package com.particeep.api.models.enums

import play.api.libs.json._

import scala.language.implicitConversions

trait Enum {
  def name: String
}

trait EnumHelper[E <: Enum] {
  def values: Set[E]

  def get(value: String): Option[E] = values.filter(t => t.name == value).headOption

  def get(valueOpt: Option[String]): Option[E] = valueOpt.flatMap(get(_))

  def isAvalaible(value: String): Boolean = get(value).isDefined

  def toString(value: E) = value.name

  implicit def ordering: Ordering[E] = Ordering.by(_.name)
  implicit def enum2string(status: E): String = toString(status)
  implicit def string2enum(value: String): Option[E] = get(value)
  implicit def optString2enum(value: Option[String]): Option[E] = get(value.getOrElse(""))

  implicit def enumReads: Reads[E] = new Reads[E] {
    def reads(json: JsValue): JsResult[E] = json match {
      case JsString(s) => get(s) match {
        case Some(enum) => JsSuccess(enum)
        case None       => JsError(s"[error] enum value $s is not in the enum possible value ${values.map(_.name).mkString(", ")}")
      }
      case _ => JsError(s"[error] unknown error while parsing value from json $json")
    }
  }

  implicit def enumWrites: Writes[E] = new Writes[E] {
    def writes(v: E): JsValue = JsString(v)
  }
}
