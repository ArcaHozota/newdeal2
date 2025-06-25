package app.preach.gospel.model

import zio.json.*

case class Role(
    id: Long,
    name: String,
    visibleFlg: Boolean
)

object Role {
  implicit val encoder: JsonEncoder[Role] = DeriveJsonEncoder.gen[Role]
  implicit val decoder: JsonDecoder[Role] = DeriveJsonDecoder.gen[Role]
}
