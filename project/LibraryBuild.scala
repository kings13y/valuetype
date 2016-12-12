import sbt.Keys._
import sbt._
import uk.gov.hmrc.DefaultBuildSettings._
import uk.gov.hmrc.SbtAutoBuildPlugin
import uk.gov.hmrc.versioning.SbtGitVersioning
import scoverage.ScoverageKeys

object LibraryBuild extends Build {

  val appName = "valuetype"

  lazy val scoverageSettings = Seq(
    ScoverageKeys.coverageExcludedPackages := "<empty>;.*BuildInfo.*",
    ScoverageKeys.coverageMinimum := 95,
    ScoverageKeys.coverageFailOnMinimum := false,
    ScoverageKeys.coverageHighlighting := true,
    ScoverageKeys.coverageEnabled := false
  )

  lazy val valuetype = Project(appName, file("."))
    .enablePlugins(SbtAutoBuildPlugin, SbtGitVersioning)
    .settings(scalaSettings ++ scoverageSettings: _*)
    .settings(
      targetJvm := "jvm-1.8",
      scalaVersion := "2.11.8",
      libraryDependencies ++= LibraryDependencies(),
      resolvers := Seq(
        Resolver.bintrayRepo("hmrc", "releases"), "typesafe-releases" at "http://repo.typesafe.com/typesafe/releases/"
      ),
      crossScalaVersions := Seq("2.11.5")
    )

}

private object LibraryDependencies {

  val compile = Seq(
    "com.typesafe.play" %% "play" % "2.5.8" % "provided",
    "com.typesafe.play" %% "play-json" % "2.5.8" % "provided"
  )

  val test = Seq(
    "org.scalacheck" %% "scalacheck" % "1.12.5" % "test",
    "org.scalatest" %% "scalatest" % "2.2.6" % "test",
    "org.pegdown" % "pegdown" % "1.4.2" % "test"
  )

  def apply() = compile ++ test
}
