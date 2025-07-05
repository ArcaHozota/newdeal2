package app.preach.gospel.repository

import zio.*
import app.preach.gospel.model.Book
import app.preach.gospel.db.DatabaseError

trait BookRepository {
  def insert(book: Book): IO[DatabaseError, Long]
  def findById(id: Short): IO[DatabaseError, Option[Book]]
  def findAll(): IO[DatabaseError, List[Book]]
}
