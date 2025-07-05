package app.preach.gospel.repository

import app.preach.gospel.model.Book
import zio.*

trait BookRepository {
  def insert(book: Book): Task[Long]
  def findById(id: Long): Task[List[Book]]
  def findAll(): Task[List[Book]]
}
