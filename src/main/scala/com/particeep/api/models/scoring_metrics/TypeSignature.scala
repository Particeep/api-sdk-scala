package com.particeep.api.models.scoring_metrics

import play.api.libs.json._

case class TypeSignature(value: Map[String, String])

object TypeSignature {

  implicit object typeSignatureWrites extends Writes[TypeSignature] {
    override def writes(ts: TypeSignature): JsValue = Json.toJson(ts.value).as[JsObject]
  }

  implicit object typeSignatureReads extends Reads[TypeSignature] {
    def reads(js: JsValue): JsResult[TypeSignature] = {

      js.validate[Map[String, String]] match {
        case JsSuccess(a_map, _) => JsSuccess(TypeSignature(a_map))
        case JsError(_)          => JsError("Invalid type signature contract")
      }
    }
  }

}