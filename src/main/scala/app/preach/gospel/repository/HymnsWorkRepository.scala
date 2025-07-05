package app.preach.gospel.repository

import app.preach.gospel.model.HymnsWork
import zio.*

trait HymnsWorkRepository {
  def insert(hymnsWork: HymnsWork): Task[Long]
  def findByWorkId(workId: Long): Task[List[HymnsWork]]
  def findAll(): Task[List[HymnsWork]]
}
