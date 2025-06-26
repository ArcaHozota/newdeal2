package app.preach.gospel.repository.impl

import app.preach.gospel.model.RoleAuth
import app.preach.gospel.repository.RoleAuthRepository
import io.getquill.*
import zio.*

import javax.sql.DataSource

final class RoleAuthRepositoryImpl(ds: DataSource) extends RoleAuthRepository {

  import app.preach.gospel.db.QuillContext.*

  override def insert(roleAuth: RoleAuth): Task[Long] =
    run(query[RoleAuth].insertValue(lift(roleAuth)))
      .provideEnvironment(ZEnvironment(ds));

  override def batchUpdateByRoleId(roleAuths: List[RoleAuth]): Task[Long] = {
    val roleId = roleAuths.headOption.map(_.roleId)
    roleId match {
      case Some(rid) =>
        transaction {
          for {
            // ① roleId に一致する既存データ削除
            _ <- run(query[RoleAuth].filter(_.roleId == lift(rid)).delete)
            // ② 新しい roleAuths をバルク挿入
            inserted <- run(
              liftQuery(roleAuths).foreach(ra =>
                query[RoleAuth].insertValue(ra)
              )
            )
          } yield inserted.sum // inserted は List[Long] なので合計件数を返す
        }.provideEnvironment(ZEnvironment(ds));
      case None =>
        ZIO.succeed(0L); // 空リストだった場合は何もしない
    }
  }

  override def findByRoleId(roleId: Long): Task[List[RoleAuth]] =
    run(
      query[RoleAuth].filter(_.roleId == lift(roleId))
    )
      .provideEnvironment(ZEnvironment(ds));

  override def findAll(): Task[List[RoleAuth]] =
    run(query[RoleAuth]).provideEnvironment(ZEnvironment(ds));

}
