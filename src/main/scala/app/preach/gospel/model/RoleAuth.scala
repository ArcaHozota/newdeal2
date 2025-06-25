package app.preach.gospel.model

import zio.json.*

case class RoleAuth(
    roleId: Long,
    authId: Long
)

object RoleAuth {
  implicit val encoder: JsonEncoder[RoleAuth] = DeriveJsonEncoder.gen[RoleAuth]
  implicit val decoder: JsonDecoder[RoleAuth] = DeriveJsonDecoder.gen[RoleAuth]
}
