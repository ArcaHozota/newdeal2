package app.preach.gospel.pojo

/** 書別情報転送クラス
  * @author
  *   ArkamaHozota
  * @since 1.00beta
  */
final case class BookDto(
    id: String, // ID
    name: String, // 名称
    nameJp: String // 日本語名称
) extends Serializable
