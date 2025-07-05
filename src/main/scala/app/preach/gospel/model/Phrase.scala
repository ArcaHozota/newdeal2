package app.preach.gospel.model

import zio.json.*

case class Phrase(
    id: Long,
    name: String,
    textEn: String,
    textJp: String,
    changeLine: Boolean,
    chapterId: Int
)

object Phrase {
  implicit val encoder: JsonEncoder[Phrase] = DeriveJsonEncoder.gen[Phrase]
  implicit val decoder: JsonDecoder[Phrase] = DeriveJsonDecoder.gen[Phrase]
}
