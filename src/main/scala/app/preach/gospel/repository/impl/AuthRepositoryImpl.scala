package app.preach.gospel.repository.impl

import app.preach.gospel.model.Auth
import app.preach.gospel.repository.AuthRepository
import io.getquill.*
import zio.*

import javax.sql.DataSource

final class AuthRepositoryImpl(ds: DataSource) extends AuthRepository {
  import app.preach.gospel.db.QuillContext.*

  override def insert(auth: Auth): Task[Long] =
    run(query[Auth].insertValue(lift(auth)))
      .provideEnvironment(ZEnvironment(ds));

  override def findById(id: Long): Task[List[Auth]] =
    run(query[Auth].filter(_.id == lift(id)))
      .provideEnvironment(ZEnvironment(ds));

  override def findAll(): Task[List[Auth]] =
    run(query[Auth]).provideEnvironment(ZEnvironment(ds));

}
