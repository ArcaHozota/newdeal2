package app.preach.gospel.repository

import zio.*
import app.preach.gospel.model.Student
import app.preach.gospel.db.DatabaseError

trait StudentRepository {
  def insert(student: Student): IO[DatabaseError, Long]
  def update(student: Student): IO[DatabaseError, Long]
  def countByLoginAccountExcludeId(
      account: String,
      id: Long
  ): IO[DatabaseError, Int]
  def findByLoginAccountOrEmail(
      account: String
  ): IO[DatabaseError, Option[Student]]
  def findById(id: Long): IO[DatabaseError, Option[Student]]
  def findAll(): IO[DatabaseError, List[Student]]
}
