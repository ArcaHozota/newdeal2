// ------------------------------------------------------------
// 基本信息
ThisBuild / scalaVersion  := "3.3.5"          // 用你项目的实际版本
ThisBuild / organization := "app.preach.gospel"

// ------------------------------------------------------------
// 依賴宣言
lazy val root = (project in file("."))
  .settings(
    name := "newdeal2",
    // 核心依賴
    libraryDependencies ++= Seq(
      // ZIO 核心協程庫（必需）
      "dev.zio" %% "zio"      % "2.1.2",
      // ZIO HTTP 3 ── 最新穩定版 3.2.0（2025-03-26 發佈）
      "dev.zio" %% "zio-http" % "3.2.0",   // ← 只有這一行即可把框架拉下來
      // ★ 可選：JSON 編碼解碼
      "dev.zio" %% "zio-json" % "0.7.39"    // 與 ZIO HTTP 默契最好
    )
  )