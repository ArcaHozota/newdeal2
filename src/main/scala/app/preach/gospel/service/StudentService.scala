package app.preach.gospel.service

import zio.*
import app.preach.gospel.pojo.StudentDto
import app.preach.gospel.db.DatabaseError

/** 奉仕者サービスインターフェース
  * @author
  *   Ark
  * @since 1.00beta
  */
trait StudentService {

  /** アカウントの重複性をチェックする
    * @param id
    *   ID
    * @param loginAccount
    *   アカウント
    * @return
    *   IO effect: 成功时返回重複件数, 失败时抛 DatabaseError
    */
  def checkDuplicated(id: String, loginAccount: String): IO[DatabaseError, Int]

  /** IDによって奉仕者の情報を取得する
    * @param id
    *   ID
    * @return
    *   IO effect: 成功时返回 StudentDto, 失败时抛 DatabaseError
    */
  def getStudentInfoById(id: Long): IO[DatabaseError, StudentDto]

  /** 奉仕者情報を更新する
    * @param studentDto
    *   奉仕者情報転送クラス
    * @return
    *   IO effect: 成功时返回结果字符串, 失败时抛 DatabaseError
    */
  def infoUpdate(studentDto: StudentDto): IO[DatabaseError, String]

  /** ログイン時間記録
    * @param loginAccount
    *   アカウント
    * @param password
    *   パスワード
    * @return
    *   IO effect: 成功时返回结果字符串, 失败时抛 DatabaseError
    */
  def preLoginUpdate(
      loginAccount: String,
      password: String
  ): IO[DatabaseError, String]
}
