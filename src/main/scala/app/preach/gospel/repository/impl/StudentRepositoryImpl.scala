package app.preach.gospel.repository.impl

import app.preach.gospel.db.{DatabaseError, DbConnectionFailed, DbQueryFailed}
import io.getquill.*

import javax.sql.DataSource
import zio.*
import app.preach.gospel.repository.StudentRepository
import app.preach.gospel.model.Student

final class StudentRepositoryImpl(ds: DataSource) extends StudentRepository {
  import app.preach.gospel.db.QuillContext.*

  override def insert(student: Student): Task[Long] =
    run(query[Student].insertValue(lift(student)))
      .provideEnvironment(ZEnvironment(ds));

  override def update(student: Student): Task[Long] =
    run(
      query[Student]
        .filter(s => s.visibleFlg == lift(true) && s.id == lift(student.id))
        .update(
          _.loginAccount -> lift(student.loginAccount),
          _.username -> lift(student.username),
          _.password -> lift(student.password),
          _.roleId -> lift(student.roleId),
          _.email -> lift(student.email),
          _.dateOfBirth -> lift(student.dateOfBirth),
          _.updatedTime -> lift(student.updatedTime)
        )
    )
      .provideEnvironment(ZEnvironment(ds));

  override def countByLoginAccountExcludeId(
      account: String,
      id: Long
  ): IO[DatabaseError, Int] =
    run(
      query[Student].filter(s =>
        s.visibleFlg == lift(true) && s.loginAccount == lift(
          account
        ) && s.id != lift(id)
      )
    )
      .map(_.size) // 用 size 获取 List 元素数量
      .provideEnvironment(ZEnvironment(ds))
      .mapError {
        case ex: java.sql.SQLException =>
          DbConnectionFailed(
            "Failed to count student by LoginAccount (DB connection error)",
            ex
          )
        case ex =>
          DbQueryFailed(
            s"Failed to count student by LoginAccount: $account",
            ex
          )
      };

  override def findByLoginAccountOrEmail(
      account: String
  ): IO[DatabaseError, Option[Student]] =
    run(
      query[Student].filter(s => s.visibleFlg == lift(true) && s.id == lift(id))
    )
      .map(_.headOption)
      .provideEnvironment(ZEnvironment(ds));

  override def findById(id: Long): IO[DatabaseError, Option[Student]] =
    run(
      query[Student].filter(s => s.visibleFlg == lift(true) && s.id == lift(id))
    )
      .map(_.headOption)
      .provideEnvironment(ZEnvironment(ds))
      .mapError {
        case ex: java.sql.SQLException =>
          DbConnectionFailed(
            "Failed to find student by ID (DB connection error)",
            ex
          )
        case ex =>
          DbQueryFailed(s"Failed to student book by ID: $id", ex)
      };

  override def findAll(): Task[List[Student]] =
    run(query[Student].filter(_.visibleFlg == lift(true)))
      .provideEnvironment(ZEnvironment(ds));

}
