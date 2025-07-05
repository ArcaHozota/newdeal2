package app.preach.gospel.repository.impl

import app.preach.gospel.model.Hymn
import app.preach.gospel.repository.HymnRepository
import io.getquill.*
import zio.*

import javax.sql.DataSource

final class HymnRepositoryImpl(ds: DataSource) extends HymnRepository {
  import app.preach.gospel.db.QuillContext.*

  override def insert(hymn: Hymn): Task[Long] =
    run(query[Hymn].insertValue(lift(hymn)))
      .provideEnvironment(ZEnvironment(ds));

  override def findById(id: Long): Task[List[Hymn]] =
    run(
      query[Hymn].filter(hm => hm.visibleFlg == lift(true) && hm.id == lift(id))
    )
      .provideEnvironment(ZEnvironment(ds));

  override def findAll(): Task[List[Hymn]] =
    run(query[Hymn]).provideEnvironment(ZEnvironment(ds));

}
