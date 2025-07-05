package app.preach.gospel.repository.impl

import app.preach.gospel.db.{DatabaseError, DbQueryFailed, DbConnectionFailed}
import app.preach.gospel.model.Book
import app.preach.gospel.repository.BookRepository
import io.getquill.*
import zio.*

import javax.sql.DataSource

final class BookRepositoryImpl(ds: DataSource) extends BookRepository {
  import app.preach.gospel.db.QuillContext.*

  override def insert(book: Book): IO[DatabaseError, Long] =
    run(query[Book].insertValue(lift(book)))
      .provideEnvironment(ZEnvironment(ds))
      .mapError {
        case ex: java.sql.SQLException =>
          DbConnectionFailed("Failed to insert book (DB connection error)", ex)
        case ex =>
          DbQueryFailed("Failed to insert book", ex)
      };

  override def findById(id: Short): IO[DatabaseError, Option[Book]] =
    run(query[Book].filter(_.id == lift(id)))
      .provideEnvironment(ZEnvironment(ds))
      .map(_.headOption)
      .mapError {
        case ex: java.sql.SQLException =>
          DbConnectionFailed(
            "Failed to find book by ID (DB connection error)",
            ex
          )
        case ex =>
          DbQueryFailed(s"Failed to find book by ID: $id", ex)
      };

  override def findAll(): IO[DatabaseError, List[Book]] =
    run(query[Book])
      .provideEnvironment(ZEnvironment(ds))
      .mapError {
        case ex: java.sql.SQLException =>
          DbConnectionFailed(
            "Failed to find all books (DB connection error)",
            ex
          )
        case ex =>
          DbQueryFailed("Failed to find all books", ex)
      };

}
