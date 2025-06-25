package app.preach.gospel.model

import zio.json.*

import java.time.OffsetDateTime

case class HymnsWork(
    id: Long,
    workId: Long,
    score: Option[Array[Byte]],
    nameJpRa: Option[String],
    updatedTime: OffsetDateTime,
    biko: Option[String]
)

object HymnsWork {
  implicit val encoder: JsonEncoder[HymnsWork] = DeriveJsonEncoder.gen[HymnsWork]
  implicit val decoder: JsonDecoder[HymnsWork] = DeriveJsonDecoder.gen[HymnsWork]
}
