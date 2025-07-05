package app.preach.gospel.repository.impl

import app.preach.gospel.db.{DatabaseError, DbConnectionFailed, DbQueryFailed}
import app.preach.gospel.model.Phrase
import app.preach.gospel.repository.PhraseRepository
import io.getquill.*
import zio.*

import javax.sql.DataSource

final class PhraseRepositoryImpl(ds: DataSource) extends PhraseRepository {
  import app.preach.gospel.db.QuillContext.*

  override def insert(phrase: Phrase): IO[DatabaseError, Long] =
    run(query[Phrase].insertValue(lift(phrase)))
      .provideEnvironment(ZEnvironment(ds))
      .mapError {
        case ex: java.sql.SQLException =>
          DbConnectionFailed(
            "Failed to insert phrase (DB connection error)",
            ex
          )
        case ex =>
          DbQueryFailed("Failed to insert phrase", ex)
      };

  override def update(phrase: Phrase): IO[DatabaseError, Long] =
    run(
      query[Phrase]
        .filter(_.id == lift(phrase.id))
        .update(
          _.name -> lift(phrase.name),
          _.textEn -> lift(phrase.textEn),
          _.textJp -> lift(phrase.textJp),
          _.changeLine -> lift(phrase.changeLine),
          _.chapterId -> lift(phrase.chapterId)
        )
    )
      .provideEnvironment(ZEnvironment(ds))
      .mapError {
        case ex: java.sql.SQLException =>
          DbConnectionFailed(
            "Failed to insert phrase (DB connection error)",
            ex
          )
        case ex =>
          DbQueryFailed("Failed to insert phrase", ex)
      };

  override def findById(id: Long): IO[DatabaseError, Option[Phrase]] =
    run(query[Phrase].filter(_.id == lift(id)))
      .provideEnvironment(ZEnvironment(ds))
      .map(_.headOption)
      .mapError {
        case ex: java.sql.SQLException =>
          DbConnectionFailed(
            "Failed to find phrase by ID (DB connection error)",
            ex
          )
        case ex =>
          DbQueryFailed(s"Failed to find phrase by ID: $id", ex)
      };

  override def findAll(): IO[DatabaseError, List[Phrase]] =
    run(query[Phrase])
      .provideEnvironment(ZEnvironment(ds))
      .mapError {
        case ex: java.sql.SQLException =>
          DbConnectionFailed(
            "Failed to find all phrases (DB connection error)",
            ex
          )
        case ex =>
          DbQueryFailed("Failed to find all phrases", ex)
      };

}
