package app.preach.gospel.pojo

/** 奉仕者情報転送クラス
  * @author
  *   ArkamaHozota
  * @since 1.00beta
  */
final case class StudentDto(
    id: String, // ID
    loginAccount: String, // アカウント
    username: String, // ユーザ名称
    password: String, // パスワード
    email: String, // メール
    dateOfBirth: String, // 生年月日
    roleId: String // 役割ID
) extends Serializable
