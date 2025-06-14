package app.preach.gospel.repository

import zio._
import app.preach.gospel.model._

trait StudentRepository {
  def create(student: Student): Task[Long]
  def findById(id: Long): Task[Option[Student]]
  def findAll(): Task[List[Student]]
}
