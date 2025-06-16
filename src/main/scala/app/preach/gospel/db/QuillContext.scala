package app.preach.gospel.db

import io.getquill._
import io.getquill.jdbczio.Quill
import zio.ZLayer

import javax.sql.DataSource

object QuillContext extends PostgresZioJdbcContext(SnakeCase) {
  val dataSourceLayer: ZLayer[Any, Throwable, DataSource] =
    Quill.DataSource.fromPrefix("dbConfig")
}