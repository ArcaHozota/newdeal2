package app.preach.gospel.model

import zio.json.*

import java.time.OffsetDateTime

case class Hymn(
    id: Long,
    nameJp: String,
    nameKr: String,
    link: Option[String],
    updatedTime: OffsetDateTime,
    updatedUser: Long,
    serif: Option[String],
    visibleFlg: Boolean
)

object Hymn {
  implicit val encoder: JsonEncoder[Hymn] = DeriveJsonEncoder.gen[Hymn]
  implicit val decoder: JsonDecoder[Hymn] = DeriveJsonDecoder.gen[Hymn]
}
