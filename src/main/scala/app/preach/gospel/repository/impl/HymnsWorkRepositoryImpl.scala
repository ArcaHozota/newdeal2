package app.preach.gospel.repository.impl

import app.preach.gospel.model.HymnsWork
import app.preach.gospel.repository.HymnsWorkRepository
import io.getquill.*
import zio.*

import javax.sql.DataSource

final class HymnsWorkRepositoryImpl(ds: DataSource)
    extends HymnsWorkRepository {

  import app.preach.gospel.db.QuillContext.*

  override def insert(hymnsWork: HymnsWork): Task[Long] =
    run(query[HymnsWork].insertValue(lift(hymnsWork)))
      .provideEnvironment(ZEnvironment(ds));

  override def findByWorkId(workId: Long): Task[List[HymnsWork]] =
    run(query[HymnsWork].filter(_.workId == lift(workId)))
      .provideEnvironment(ZEnvironment(ds));

  override def findAll(): Task[List[HymnsWork]] =
    run(query[HymnsWork]).provideEnvironment(ZEnvironment(ds));

}
