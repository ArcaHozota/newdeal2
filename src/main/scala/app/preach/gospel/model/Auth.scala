package app.preach.gospel.model

import zio.json.*

case class Auth(
    id: Long,
    name: String,
    title: String,
    categoryId: Option[Long]
)

object Auth {
  implicit val encoder: JsonEncoder[Auth] = DeriveJsonEncoder.gen[Auth]
  implicit val decoder: JsonDecoder[Auth] = DeriveJsonDecoder.gen[Auth]
}
