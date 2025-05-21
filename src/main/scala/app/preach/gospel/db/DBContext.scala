package app.preach.gospel.db

import io.getquill.*
import zio.*
import zio.config.*
import zio.config.typesafe.*
import zio.config.magnolia.*
import zio.config.magnolia.DeriveConfig.* // ✅ 自动导出 Config[DbConfig]

final case class DbConfig(
    url: String,
    user: String,
    password: String,
    driver: String
)

object DbContext {
  lazy val live: ZLayer[Any, Throwable, PostgresZioJdbcContext[SnakeCase]] =
    ZLayer.scoped {
      val configProvider = TypesafeConfigProvider.fromResourcePath() // ❗直接作为值
      for {
        config <- configProvider.load[DbConfig] // ✅ 这里只是调用返回 ZIO
        _ <- ZIO.logInfo(s"Loaded DB config: ${config.url}")
        ctx <- ZIO.attempt(new PostgresZioJdbcContext(SnakeCase))
      } yield ctx
    }
}
