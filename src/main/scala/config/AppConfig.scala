package config

import zio._
import zio.config._
import zio.config.magnolia._
import zio.config.typesafe._

// 定义配置模型
final case class HttpConfig(host: String, port: Int)
final case class DbConfig(url: String, user: String, password: String)
final case class AppConfig(http: HttpConfig, db: DbConfig)

object AppConfig {
  // 自动派生配置描述
  val descriptor = descriptor[AppConfig]
  // 创建一个 ZLayer 来提供配置
  val layer: ZLayer[Any, ReadError[String], AppConfig] =
    TypesafeConfig.fromResourcePath(descriptor)
}
