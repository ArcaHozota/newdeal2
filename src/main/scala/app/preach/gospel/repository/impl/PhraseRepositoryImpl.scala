package app.preach.gospel.repository.impl

import app.preach.gospel.model.Phrase
import app.preach.gospel.repository.PhraseRepository
import io.getquill.*
import zio.*

import javax.sql.DataSource

final class PhraseRepositoryImpl(ds: DataSource) extends PhraseRepository {

  import app.preach.gospel.db.QuillContext.*

  override def insert(phrase: Phrase): Task[Long] =
    run(query[Phrase].insertValue(lift(phrase)))
      .provideEnvironment(ZEnvironment(ds));

  override def findById(id: Long): Task[List[Phrase]] =
    run(query[Phrase].filter(_.id == lift(id)))
      .provideEnvironment(ZEnvironment(ds));

  override def findAll(): Task[List[Phrase]] =
    run(query[Phrase]).provideEnvironment(ZEnvironment(ds));

}
