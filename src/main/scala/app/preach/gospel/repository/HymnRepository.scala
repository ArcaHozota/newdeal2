package app.preach.gospel.repository

import app.preach.gospel.model.Hymn
import zio.*

trait HymnRepository {
  def insert(hymn: Hymn): Task[Long]
  def findById(id: Long): Task[List[Hymn]]
  def findAll(): Task[List[Hymn]]
}
