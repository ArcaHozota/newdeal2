package app.preach.gospel.repository

import app.preach.gospel.model.RoleAuth
import zio.*

trait RoleAuthRepository {
  def insert(roleAuth: RoleAuth): Task[Long]
  def batchUpdateByRoleId(roleAuths: List[RoleAuth]): Task[Long]
  def findByRoleId(roleId: Long): Task[List[RoleAuth]]
  def findAll(): Task[List[RoleAuth]]
}
