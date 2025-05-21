package app.preach.gospel.db

import io.getquill._
import zio._
import zio.config._
import zio.config.typesafe.TypesafeConfigProvider

final case class DbConfig(
    url: String,
    user: String,
    password: String,
    driver: String
)

object DbContext {
  lazy val live: ZLayer[Any, Throwable, PostgresZioJdbcContext[SnakeCase]] = {
    ZLayer.scoped {
      for {
        config <- ZIO
          .config(DbConfigDescriptor)
          .provide(TypesafeConfigProvider.fromResourcePath())
        ctx <- ZIO.attempt {
          new PostgresZioJdbcContext(SnakeCase)
        }
      } yield ctx
    }
  }

  val DbConfigDescriptor: Config[DbConfig] =
    (descriptor[String]("db.url") zip
      descriptor[String]("db.user") zip
      descriptor[String]("db.password") zip
      descriptor[String]("db.driver")).to[DbConfig]
}
