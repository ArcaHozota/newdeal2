package app.preach.gospel.service.impl

import zio.*
import app.preach.gospel.pojo.{BookDto, ChapterDto, PhraseDto}
import app.preach.gospel.db.{DatabaseError, DbQueryFailed}
import app.preach.gospel.repository.*
import app.preach.gospel.model.{Book, Phrase}
import app.preach.gospel.service.BookService

final class BookServiceImpl(
    bookRepo: BookRepository,
    chapterRepo: ChapterRepository,
    phraseRepo: PhraseRepository
) extends BookService {

  override def getBooks(): IO[DatabaseError, List[BookDto]] = {
    // 1. 查询数据库，得到 List[Book]（Book 为你的 entity case class）
    val queryEffect: IO[DatabaseError, List[Book]] =
      bookRepo.findAll() // Quill 查询
    // 2. 转换成 BookDto
    val result: IO[DatabaseError, List[BookDto]] = {
      queryEffect.map { books =>
        books.map(book =>
          BookDto(
            id = book.id.toString,
            name = book.name,
            nameJp = book.nameJp
          )
        )
      }
    }
    result
  }

  override def getChaptersByBookId(
      id: String
  ): IO[DatabaseError, List[ChapterDto]] = {
    val bookId: Short =
      if (id.forall(_.isDigit)) id.toShort else 1
    chapterRepo.findByBookId(bookId).map { chapters =>
      chapters.map { chapter =>
        ChapterDto(
          id = chapter.id.toString,
          name = chapter.name,
          nameJp = chapter.nameJp,
          bookId = chapter.bookId.toString
        )
      }
    }
  }

  override def infoStorage(phraseDto: PhraseDto): IO[DatabaseError, String] = {
    val id = phraseDto.id.toLong
    val chapterId = phraseDto.chapterId.toInt
    for {
      chapterOpt <- chapterRepo.findById(chapterId)
      chapter <- ZIO
        .fromOption(chapterOpt)
        .orElseFail(
          DbQueryFailed(s"Chapter not found with ID: $chapterId", null)
        )
      composedName = s"${chapter.name}:$id"
      textEn =
        if (phraseDto.textEn.endsWith("#"))
          phraseDto.textEn.stripSuffix("#") -> true
        else
          phraseDto.textEn -> false
      existingPhrase <- phraseRepo.findById(chapterId * 1000 + id)
      result <- existingPhrase match {
        case Some(phrase) =>
          val updatedPhrase = phrase.copy(
            name = composedName,
            textEn = textEn._1,
            textJp = phraseDto.textJp,
            changeLine = textEn._2,
            chapterId = chapterId
          )
          phraseRepo
            .update(updatedPhrase)
            .as("String updated")
        case None =>
          val newPhrase = Phrase(
            id = (chapterId * 1000) + id,
            name = composedName,
            textEn = textEn._1,
            textJp = phraseDto.textJp,
            changeLine = textEn._2,
            chapterId = chapterId
          )
          phraseRepo
            .insert(newPhrase)
            .as("String inserted")
      }
    } yield result
  }

}
