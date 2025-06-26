package app.preach.gospel.repository.impl

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
        .updateValue(lift(student))
    )
      .provideEnvironment(ZEnvironment(ds));

  override def findById(id: Long): Task[Option[Student]] =
    run(
      query[Student].filter(s => s.visibleFlg == lift(true) && s.id == lift(id))
    )
      .map(_.headOption)
      .provideEnvironment(ZEnvironment(ds));

  override def findAll(): Task[List[Student]] =
    run(query[Student].filter(_.visibleFlg == lift(true)))
      .provideEnvironment(ZEnvironment(ds));

}
