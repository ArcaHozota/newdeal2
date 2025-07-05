package app.preach.gospel.repository.impl

import app.preach.gospel.model.Book
import app.preach.gospel.repository.BookRepository
import io.getquill.*
import zio.*

import javax.sql.DataSource

final class BookRepositoryImpl(ds: DataSource) extends BookRepository {
  import app.preach.gospel.db.QuillContext.*

  override def insert(book: Book): Task[Long] =
    run(query[Book].insertValue(lift(book)))
      .provideEnvironment(ZEnvironment(ds));

  override def findById(id: Long): Task[List[Book]] =
    run(query[Book].filter(_.id == lift(id)))
      .provideEnvironment(ZEnvironment(ds));

  override def findAll(): Task[List[Book]] =
    run(query[Book]).provideEnvironment(ZEnvironment(ds));

}
