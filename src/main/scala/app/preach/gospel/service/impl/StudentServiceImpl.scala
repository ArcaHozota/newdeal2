package app.preach.gospel.service.impl

import zio.*

import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import app.preach.gospel.pojo.StudentDto
import app.preach.gospel.db.{DatabaseError, DbQueryFailed}
import app.preach.gospel.db.DatabaseError
import app.preach.gospel.repository.StudentRepository
import app.preach.gospel.model.Student
import app.preach.gospel.service.StudentService

final class StudentServiceImpl(studentRepo: StudentRepository)
    extends StudentService {

  private val encoder = new BCryptPasswordEncoder(7)
  private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

  override def checkDuplicated(
      id: String,
      loginAccount: String
  ): IO[DatabaseError, Int] = {
    val checkEffect =
      if (id.forall(_.isDigit))
        studentRepo.countByLoginAccountExcludeId(loginAccount, id.toLong)
      else
        studentRepo.countByLoginAccountExcludeId(loginAccount, 0)
    checkEffect
  }

  override def getStudentInfoById(id: Long): IO[DatabaseError, StudentDto] =
    studentRepo.findById(id).flatMap {
      case Some(student) =>
        ZIO.succeed(
          StudentDto(
            id = student.id.toString,
            loginAccount = student.loginAccount,
            username = student.username,
            password = student.password,
            email = student.email.get,
            dateOfBirth = formatter.format(student.dateOfBirth),
            roleId = student.roleId.toString
          )
        )
      case None =>
        ZIO.fail(DbQueryFailed(s"Student not found with ID: $id", null))
    }

  override def infoUpdate(
      studentDto: StudentDto
  ): IO[DatabaseError, String] = {
    val studentId = studentDto.id.toLong
    for {
      newStudent <- ZIO.succeed(
        // 从 DTO 创建临时更新对象
        Student(
          id = studentId,
          loginAccount = studentDto.loginAccount,
          username = studentDto.username,
          dateOfBirth = LocalDate.parse(studentDto.dateOfBirth, formatter),
          email = Option.apply(studentDto.email),
          password = "", // placeholder, 等比较后再设
          updatedTime = None,
          visibleFlg = true
        )
      )
      existingOpt <- studentRepo.findById(studentId)
      existing <- ZIO
        .fromOption(existingOpt)
        .orElseFail(
          DbQueryFailed(s"Student not found for update: $studentId", null)
        )
      passwordUnchanged = studentDto.password == existing.password || encoder
        .matches(studentDto.password, existing.password)
      changeDetected = newStudent.copy(password = null) != existing
        .copy(password = null)
      _ <-
        if (!changeDetected && passwordUnchanged)
          ZIO.fail(DbQueryFailed("No changes detected", null))
        else
          ZIO.unit
      updatedStudent = existing.copy(
        loginAccount = newStudent.loginAccount,
        username = newStudent.username,
        dateOfBirth = newStudent.dateOfBirth,
        email = newStudent.email,
        password =
          if (passwordUnchanged) existing.password
          else encoder.encode(studentDto.password),
        updatedTime = Some(existing.updatedTime.getOrElse(OffsetDateTime.now()))
      )
      _ <- studentRepo.update(updatedStudent)
    } yield "Student information updated successfully"
  }

  override def preLoginUpdate(
      loginAccount: String,
      password: String
  ): IO[DatabaseError, String] = {
    for {
      studentOpt <- studentRepo.findByLoginAccountOrEmail(loginAccount)
      student <- ZIO
        .fromOption(studentOpt)
        .orElseFail(DbQueryFailed("Login error: account not found", null))
      passwordOk = encoder.matches(password, student.password)
      _ <-
        if (!passwordOk)
          ZIO.fail(DbQueryFailed("Login error: incorrect password", null))
        else
          ZIO.unit
      updatedStudent = student.copy(updatedTime = Some(OffsetDateTime.now()))
      _ <- studentRepo.update(updatedStudent)
    } yield "Login successful"
  }

}
