package app.preach.gospel.db

import io.getquill._

object QuillContext extends PostgresZioJdbcContext(SnakeCase)
