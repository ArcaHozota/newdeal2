// ------------------------------------------------------------
// 基本信息
ThisBuild / scalaVersion := "3.3.5" // 用你项目的实际版本
ThisBuild / organization := "app.preach.gospel"

val zioVersion: String = "2.1.18"
val zioConfigVersion: String = "4.0.4"
val zioHttpVersion: String = "3.2.0"
// ------------------------------------------------------------
// 依賴宣言
lazy val root = (project in file("."))
  .settings(
    name := "newdeal2",
    // 核心依賴
    libraryDependencies ++= Seq(
      // https://mvnrepository.com/artifact/dev.zio/zio
      "dev.zio" %% "zio" % zioVersion,
      // ZIO HTTP 3 ── 最新穩定版 3.2.0（2025-03-26 發佈）
      "dev.zio" %% "zio-http" % zioHttpVersion, // ← 只有這一行即可把框架拉下來
      "dev.zio" %% "zio-config" % zioConfigVersion,
      "dev.zio" %% "zio-config-magnolia" % zioConfigVersion,
      "dev.zio" %% "zio-config-typesafe" % zioConfigVersion,
      // ★ 可選：JSON 編碼解碼
      "dev.zio" %% "zio-json" % "0.7.39", // 與 ZIO HTTP 默契最好
      // https://mvnrepository.com/artifact/io.getquill/quill-jdbc-zio
      "io.getquill" %% "quill-jdbc-zio" % "4.8.6",
      // https://mvnrepository.com/artifact/org.postgresql/postgresql
      "org.postgresql" % "postgresql" % "42.7.5"
    )
  )
