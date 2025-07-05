package app.preach.gospel.repository

import zio.*
import app.preach.gospel.model.Chapter
import app.preach.gospel.db.DatabaseError

trait ChapterRepository {
  def insert(chapter: Chapter): IO[DatabaseError, Long]
  def findById(id: Int): IO[DatabaseError, Option[Chapter]]
  def findByBookId(bookId: Short): IO[DatabaseError, List[Chapter]]
  def findAll(): IO[DatabaseError, List[Chapter]]
}
