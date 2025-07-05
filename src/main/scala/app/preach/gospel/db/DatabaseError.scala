package app.preach.gospel.db

sealed trait DatabaseError extends Exception {
  def message: String
  override def getMessage: String = message
}

case class DbQueryFailed(message: String, cause: Throwable)
    extends DatabaseError
case class DbConnectionFailed(message: String, cause: Throwable)
    extends DatabaseError
