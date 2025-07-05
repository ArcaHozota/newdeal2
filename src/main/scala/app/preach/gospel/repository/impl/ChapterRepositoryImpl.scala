package app.preach.gospel.repository.impl

import app.preach.gospel.model.Chapter
import app.preach.gospel.repository.ChapterRepository
import io.getquill.*
import zio.*

import javax.sql.DataSource

final class ChapterRepositoryImpl(ds: DataSource) extends ChapterRepository {

  import app.preach.gospel.db.QuillContext.*

  override def insert(chapter: Chapter): Task[Long] =
    run(query[Chapter].insertValue(lift(chapter)))
      .provideEnvironment(ZEnvironment(ds));

  override def findById(id: Long): Task[List[Chapter]] =
    run(query[Chapter].filter(_.id == lift(id)))
      .provideEnvironment(ZEnvironment(ds));

  override def findAll(): Task[List[Chapter]] =
    run(query[Chapter]).provideEnvironment(ZEnvironment(ds));

}
