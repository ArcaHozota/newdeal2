package app.preach.gospel.model

import zio.json.*

import java.time.{LocalDate, OffsetDateTime}

case class Student(
    id: Long,
    loginAccount: String,
    password: String,
    username: String,
    email: Option[String],
    dateOfBirth: LocalDate,
    roleId: Long,
    updatedTime: Option[OffsetDateTime],
    visibleFlg: Boolean
)

object Student {
  implicit val encoder: JsonEncoder[Student] = DeriveJsonEncoder.gen[Student]
  implicit val decoder: JsonDecoder[Student] = DeriveJsonDecoder.gen[Student]
}
