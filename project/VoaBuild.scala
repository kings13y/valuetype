import sbt.Keys._
import sbt._
import uk.gov.hmrc.SbtAutoBuildPlugin
import uk.gov.hmrc.versioning.SbtGitVersioning

object VoaBuild extends Build {

  val appName = "valuetype"

  lazy val valuetype = Project(appName, file("."))
    .enablePlugins(SbtAutoBuildPlugin, SbtGitVersioning)
    .settings(organization := "uk.gov.voa")
    .settings(
      libraryDependencies ++= AppDependencies(),
      resolvers := Seq(
        Resolver.bintrayRepo("hmrc", "releases"), "typesafe-releases" at "http://repo.typesafe.com/typesafe/releases/"
      ),
      crossScalaVersions := Seq("2.11.5")
    )

}

private object AppDependencies {

  val compile = Seq(
    "com.typesafe.play" %% "play-json" % "2.3.10" % "provided"
  )

  val test = Seq(
    "org.scalatest" %% "scalatest" % "2.2.6" % "test",
    "org.pegdown" % "pegdown" % "1.4.2" % "test",
    "org.scalacheck" %% "scalacheck" % "1.12.5" % "test"
  )

  def apply() = compile ++ test
}
