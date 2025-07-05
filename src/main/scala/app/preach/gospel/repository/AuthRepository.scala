package app.preach.gospel.repository

import app.preach.gospel.model.Auth
import zio.*

trait AuthRepository {
  def insert(auth: Auth): Task[Long]
  def findById(id: Long): Task[List[Auth]]
  def findAll(): Task[List[Auth]]
}
