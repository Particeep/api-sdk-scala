package com.particeep.api.models.enums

import play.api.libs.json._

trait ControlBlockType extends Product with Serializable with Enum

object ControlBlockType {

  private[this] def enumReads: Reads[ControlBlockType] = new Reads[ControlBlockType] {
    def reads(json: JsValue): JsResult[ControlBlockType] = {
      json
        .validate[ControlControlBlockType]
        .orElse(json.validate[TransactionControlBlockType])
        .orElse(JsError(s"Can't parse json into ControlBlockType, no implementation found for $json"))
    }
  }

  private[this] def enumWrites: Writes[ControlBlockType] = new Writes[ControlBlockType] {
    def writes(v: ControlBlockType): JsValue = v match {
      case blockType: ControlControlBlockType     => Json.toJson(blockType)
      case blockType: TransactionControlBlockType => Json.toJson(blockType)
      case _                                      => throw new IllegalStateException(s"Can't serialize case class to json, no implementation found for $v")
    }
  }

  implicit val controlBlockTypeFormat: Format[ControlBlockType] = Format(enumReads, enumWrites)
}
