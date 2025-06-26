package app.preach.gospel.repository

import app.preach.gospel.model.Role
import zio.*

trait RoleRepository {
  def insert(role: Role): Task[Long]
  def update(role: Role): Task[Long]
  def findById(id: Long): Task[Option[Role]]
  def findAll(): Task[List[Role]]
}
