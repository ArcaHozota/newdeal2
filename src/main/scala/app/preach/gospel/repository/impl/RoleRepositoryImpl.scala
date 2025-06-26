package app.preach.gospel.repository.impl

import app.preach.gospel.model.Role
import app.preach.gospel.repository.RoleRepository
import io.getquill.*
import zio.*

import javax.sql.DataSource

final class RoleRepositoryImpl(ds: DataSource) extends RoleRepository {
  import app.preach.gospel.db.QuillContext.*

  override def insert(role: Role): Task[Long] =
    run(query[Role].insertValue(lift(role)))
      .provideEnvironment(ZEnvironment(ds));

  override def update(role: Role): Task[Long] =
    run(
      query[Role]
        .filter(s => s.visibleFlg == lift(true) && s.id == lift(role.id))
        .updateValue(lift(role))
    )
      .provideEnvironment(ZEnvironment(ds));

  override def findById(id: Long): Task[Option[Role]] =
    run(
      query[Role].filter(s => s.visibleFlg == lift(true) && s.id == lift(id))
    )
      .map(_.headOption)
      .provideEnvironment(ZEnvironment(ds));

  override def findAll(): Task[List[Role]] =
    run(query[Role].filter(_.visibleFlg == lift(true)))
      .provideEnvironment(ZEnvironment(ds));

}
