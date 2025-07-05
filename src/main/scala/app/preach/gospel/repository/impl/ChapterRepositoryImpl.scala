package app.preach.gospel.repository.impl

import app.preach.gospel.db.{DatabaseError, DbConnectionFailed, DbQueryFailed}
import app.preach.gospel.model.Chapter
import app.preach.gospel.repository.ChapterRepository
import io.getquill.*
import zio.*

import javax.sql.DataSource

final class ChapterRepositoryImpl(ds: DataSource) extends ChapterRepository {
  import app.preach.gospel.db.QuillContext.*

  override def insert(chapter: Chapter): IO[DatabaseError, Long] =
    run(query[Chapter].insertValue(lift(chapter)))
      .provideEnvironment(ZEnvironment(ds))
      .mapError {
        case ex: java.sql.SQLException =>
          DbConnectionFailed(
            "Failed to insert chapter (DB connection error)",
            ex
          )
        case ex =>
          DbQueryFailed("Failed to insert chapter", ex)
      };

  override def findById(id: Int): IO[DatabaseError, Option[Chapter]] =
    run(query[Chapter].filter(_.id == lift(id)))
      .provideEnvironment(ZEnvironment(ds))
      .map(_.headOption)
      .mapError {
        case ex: java.sql.SQLException =>
          DbConnectionFailed(
            "Failed to find chapter by ID (DB connection error)",
            ex
          )
        case ex =>
          DbQueryFailed(s"Failed to find chapter by ID: $id", ex)
      };

  override def findByBookId(bookId: Short): IO[DatabaseError, List[Chapter]] =
    run(query[Chapter].filter(_.bookId == lift(bookId)))
      .provideEnvironment(ZEnvironment(ds))
      .mapError {
        case ex: java.sql.SQLException =>
          DbConnectionFailed(
            "Failed to find chapters by bookId (DB connection error)",
            ex
          )
        case ex =>
          DbQueryFailed("Failed to find chapters by bookId", ex)
      };

  override def findAll(): IO[DatabaseError, List[Chapter]] =
    run(query[Chapter])
      .provideEnvironment(ZEnvironment(ds))
      .mapError {
        case ex: java.sql.SQLException =>
          DbConnectionFailed(
            "Failed to find all chapters (DB connection error)",
            ex
          )
        case ex =>
          DbQueryFailed("Failed to find all chapters", ex)
      };

}
