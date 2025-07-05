package app.preach.gospel.repository

import app.preach.gospel.model.Chapter
import zio.*

trait ChapterRepository {
  def insert(chapter: Chapter): Task[Long]
  def findById(id: Long): Task[List[Chapter]]
  def findAll(): Task[List[Chapter]]
}
