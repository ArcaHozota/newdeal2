package app.preach.gospel

import zio.*
import zio.http.*
import app.preach.gospel.db.*
import app.preach.gospel.model.*

// object MainApp extends ZIOAppDefault {

//   override def run: ZIO[Environment with ZIOAppArgs with Scope, Any, Any] = {
//     val appLogic = for {
//       ctx <- ZIO.service[PostgresZioJdbcContext[SnakeCase]]
//       repo = new UserRepo(ctx)
//       _ <- repo.create(User(1, "Alice", 30))
//       all <- repo.readAll
//       _ <- Console.printLine(s"Users: $all")
//     } yield ()

//     appLogic.provide(DbContext.live)
//   }
// }
