package app.preach.gospel.service

import zio.*
import app.preach.gospel.pojo.{BookDto, ChapterDto, PhraseDto}
import app.preach.gospel.db.DatabaseError

// 假设 DataAccessException 在你的项目里已经定义或引入
trait BookService {

  /** 聖書書別情報を取得する
    * @return
    *   ZIO effect: 成功时得到 List[BookDto], 失败时抛 DataAccessException
    */
  def getBooks(): IO[DatabaseError, List[BookDto]]

  /** 聖書章節情報を取得する
    * @param id
    *   書別ID
    * @return
    *   ZIO effect: 成功时得到 List[ChapterDto], 失败时抛 DataAccessException
    */
  def getChaptersByBookId(id: String): IO[DatabaseError, List[ChapterDto]]

  /** 聖書節別情報を保存する
    * @param phraseDto
    *   節別信息
    * @return
    *   ZIO effect: 成功时返回 String, 失败时抛 DataAccessException
    */
  def infoStorage(phraseDto: PhraseDto): IO[DatabaseError, String]
}
