package model

import zio.json._
import java.time.OffsetDateTime

case class Student(
    id: Long,
    loginAccount: String,
    password: String,
    username: String,
    email: Option[String],
    dateOfBirth: String,
    roleId: Long,
    updatedTime: Option[OffsetDateTime],
    visibleFlg: Boolean
)

object Student {
  implicit val encoder: JsonEncoder[Student] = DeriveJsonEncoder.gen[Student]
  implicit val decoder: JsonDecoder[Student] = DeriveJsonDecoder.gen[Student]
}
