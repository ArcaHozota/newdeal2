import zio._
import config.AppConfig
import repository.StudentRepository

// 在你的应用中使用
object Main extends ZIOAppDefault {
  def run = {
    // 应用HTTP服务和其他逻辑
    for {
      config <- ZIO.service[AppConfig]
      _ <- Console.printLine(
        s"Starting server at ${config.http.host}:${config.http.port}"
      )

      // 创建HTTP应用
      app = StudentApi.routes

      // 启动服务器
      server <- zio.http.Server
        .serve(app)
        .provide(
          zio.http.Server.defaultWithPort(config.http.port),
          StudentRepository.layer
        )
    } yield ()
  }.provide(
    AppConfig.layer
  )
}
