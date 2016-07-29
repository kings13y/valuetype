import sbt.Keys._
import sbt._
import uk.gov.hmrc.DefaultBuildSettings._
import uk.gov.hmrc.SbtAutoBuildPlugin
import uk.gov.hmrc.versioning.SbtGitVersioning
import scoverage.ScoverageKeys

object VoaBuild extends Build {

  val appName = "valuetype"

  lazy val scoverageSettings = Seq(
    ScoverageKeys.coverageExcludedPackages := "<empty>;.*BuildInfo.*",
    ScoverageKeys.coverageMinimum := 80,
    ScoverageKeys.coverageFailOnMinimum := false,
    ScoverageKeys.coverageHighlighting := true,
    ScoverageKeys.coverageEnabled := false
  )

  lazy val valuetype = Project(appName, file("."))
    .enablePlugins(SbtAutoBuildPlugin, SbtGitVersioning)
    .settings(scalaSettings ++ scoverageSettings: _*)
    .settings(organization := "uk.gov.voa")
    .settings(
      targetJvm := "jvm-1.8",
      libraryDependencies ++= AppDependencies(),
      resolvers := Seq(
        Resolver.bintrayRepo("hmrc", "releases"), "typesafe-releases" at "http://repo.typesafe.com/typesafe/releases/"
      ),
      crossScalaVersions := Seq("2.11.5")
    )

}

private object AppDependencies {

  val compile = Seq(
    "com.typesafe.play" %% "play" % "2.3.10" % "provided",
    "com.typesafe.play" %% "play-json" % "2.3.10" % "provided"
  )

  val test = Seq(
    "org.scalatest" %% "scalatest" % "2.2.6" % "test",
    "org.pegdown" % "pegdown" % "1.4.2" % "test",
    "org.scalacheck" %% "scalacheck" % "1.12.5" % "test"
  )

  def apply() = compile ++ test
}
