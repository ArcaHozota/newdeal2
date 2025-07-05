package app.preach.gospel.repository

import app.preach.gospel.model.Phrase
import zio.*

trait PhraseRepository {
  def insert(phrase: Phrase): Task[Long]
  def findById(id: Long): Task[List[Phrase]]
  def findAll(): Task[List[Phrase]]
}
