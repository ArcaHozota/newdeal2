package app.preach.gospel.repository

import app.preach.gospel.db.DatabaseError
import app.preach.gospel.model.Phrase
import zio.*

trait PhraseRepository {
  def insert(phrase: Phrase): IO[DatabaseError, Long]
  def update(phrase: Phrase): IO[DatabaseError, Long]
  def findById(id: Long): IO[DatabaseError, Option[Phrase]]
  def findAll(): IO[DatabaseError, List[Phrase]]
}
