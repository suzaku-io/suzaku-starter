import sbt._
import Keys._

val suzakuVersion = "0.1.0-SNAPSHOT"

val commonSettings = Seq(
  scalaVersion := "2.12.3",
  scalacOptions ++= Seq("-unchecked", "-feature", "-deprecation", "-encoding", "utf8"),
  libraryDependencies ++= Seq(
    "org.scalatest" %%% "scalatest" % "3.0.1" % Test
  ),
  resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
)

lazy val shared = crossProject.crossType(CrossType.Pure)
  .in(file("shared"))
  .settings(commonSettings: _*)

lazy val sharedJvm = shared.jvm
lazy val sharedJs = shared.js

lazy val webUI = project
  .in(file("web-ui"))
  .enablePlugins(ScalaJSPlugin)
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      "io.suzaku" %%% "suzaku-core-web"    % suzakuVersion,
      "io.suzaku" %%% "suzaku-widgets-web" % suzakuVersion
    )
  )
  .dependsOn(sharedJs)

lazy val client = project
  .in(file("client"))
  .enablePlugins(ScalaJSPlugin)
  .settings(commonSettings: _*)
  .settings(
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++= Seq(
      "io.suzaku" %%% "suzaku-core"    % suzakuVersion,
      "io.suzaku" %%% "suzaku-widgets" % suzakuVersion
    )
  )
  .dependsOn(sharedJs, webUI)

lazy val server = project
  .in(file("server"))
  .settings(commonSettings: _*)
  .settings(
    scalaJSProjects := Seq(client, webUI),
    pipelineStages in Assets := Seq(scalaJSPipeline),
    // triggers scalaJSPipeline when using compile or continuous compilation
    compile in Compile := ((compile in Compile) dependsOn scalaJSPipeline).value,
    WebKeys.packagePrefix in Assets := "public/",
    managedClasspath in Runtime += (packageBin in Assets).value,
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http" % "10.0.10"
    )
  )
  .enablePlugins(SbtWeb, JavaAppPackaging)

// loads the server project at sbt startup
onLoad in Global := (Command.process("project server", _: State)) compose (onLoad in Global).value
