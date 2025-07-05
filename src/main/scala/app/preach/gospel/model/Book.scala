package app.preach.gospel.model

import zio.json.*

case class Book(
    id: Short,
    name: String,
    nameJp: String
)

object Book {
  implicit val encoder: JsonEncoder[Book] = DeriveJsonEncoder.gen[Book]
  implicit val decoder: JsonDecoder[Book] = DeriveJsonDecoder.gen[Book]
}
