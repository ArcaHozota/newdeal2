package app.preach.gospel.pojo

/** 章節情報転送クラス
  * @author
  *   ArkamaHozota
  * @since 1.00beta
  */
final case class ChapterDto(
    id: String, // ID
    name: String, // 名称
    nameJp: String, // 日本語名称
    bookId: String // 書別ID
) extends Serializable
