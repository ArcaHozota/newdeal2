package app.preach.gospel.model

import zio.json.*

case class Chapter(
    id: Int,
    name: String,
    nameJp: String,
    bookId: Short
)

object Chapter {
  implicit val encoder: JsonEncoder[Chapter] = DeriveJsonEncoder.gen[Chapter]
  implicit val decoder: JsonDecoder[Chapter] = DeriveJsonDecoder.gen[Chapter]
}
