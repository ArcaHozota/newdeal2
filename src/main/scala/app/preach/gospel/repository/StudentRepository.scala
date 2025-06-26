package app.preach.gospel.repository

import zio.*
import app.preach.gospel.model.Student

trait StudentRepository {
  def insert(student: Student): Task[Long]
  def update(student: Student): Task[Long]
  def findById(id: Long): Task[Option[Student]]
  def findAll(): Task[List[Student]]
}
