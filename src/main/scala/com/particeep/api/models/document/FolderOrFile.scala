package com.particeep.api.models.document

import play.api.libs.json._
import play.api.libs.functional.syntax._

/**
 * Created by Noe on 17/02/2017.
 */
case class FolderOrFile(
  document:  Document,
  documents: Option[Seq[FolderOrFile]]
)

object FolderOrFile {
  implicit val document_format = Document.format

  implicit val reads: Reads[FolderOrFile] = (
    (__ \ "document").read[Document] and
    (__ \ "documents").lazyReadNullable(Reads.seq[FolderOrFile](reads))
  )(FolderOrFile.apply _)

  implicit val writes: Writes[FolderOrFile] = (
    (__ \ "document").write[Document] and
    (__ \ "documents").lazyWriteNullable(Writes.seq[FolderOrFile](writes))
  )(unlift(FolderOrFile.unapply))

  val format: Format[FolderOrFile] = Format(reads, writes)
}
