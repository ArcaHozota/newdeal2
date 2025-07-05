package app.preach.gospel.pojo

/** 節別情報転送クラス
  * @author
  *   ArkamaHozota
  * @since 1.00beta
  */
final case class PhraseDto(
    id: String, // ID
    name: String, // 名称
    textEn: String, // 内容
    textJp: String, // 日本語内容
    chapterId: String // 章節ID
) extends Serializable
