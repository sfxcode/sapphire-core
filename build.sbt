import sbt.url

import scala.sys.process._

name := "sapphire-core"

organization := "com.sfxcode.sapphire"

crossScalaVersions := Seq("2.12.8", "2.11.12")

scalaVersion := crossScalaVersions.value.head



javacOptions ++= Seq(
  "-target", "1.8",
  "-source", "1.8",
  "-Xlint:deprecation")

scalacOptions += "-deprecation"

parallelExecution in Test := false

lazy val sapphire_core_root = Project(id = "sapphire-core", base = file(".")).
  configs(IntegrationTest).
  settings(Defaults.itSettings: _*)

// resolvers

resolvers += "sonatype-snapshots" at "http://oss.sonatype.org/content/repositories/snapshots"

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"


// Test

libraryDependencies += "org.specs2" %% "specs2-core" % "4.5.1" % Test

libraryDependencies += "org.json4s" %% "json4s-native" % "3.6.5" % Test

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3" % Test



// Compile

val JavaFXVersion = "12.0.1"
val ScalaFXVersion = "12.0.1-R17"

val osName = System.getProperty("os.name") match {
  case n if n.startsWith("Linux") => "linux"
  case n if n.startsWith("Mac") => "mac"
  case n if n.startsWith("Windows") => "win"
  case _ => throw new Exception("Unknown platform!")
}

libraryDependencies ++= Seq("base", "controls", "fxml", "graphics", "media", "swing", "web").map(
  m => "org.openjfx" % s"javafx-$m" % JavaFXVersion % Provided classifier osName)

// Environment

lazy val scalaLoggingVersion = SettingKey[String]("scalaLoggingVersion")

scalaLoggingVersion := (scalaBinaryVersion.value match {
  case "2.11" => "3.9.0"
  case _ => "3.9.2"
})

libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion.value

libraryDependencies += "com.typesafe" % "config" % "1.3.4"

// UI

libraryDependencies += "org.scalafx" %% "scalafx" % ScalaFXVersion

// CDI

libraryDependencies += "javax.enterprise" % "cdi-api" % "2.0"

libraryDependencies += "javax.annotation" % "javax.annotation-api" % "1.3.2"

libraryDependencies += "org.apache.openwebbeans" % "openwebbeans-impl" % "2.0.10"

val DeltaspikeVersion = "1.9.0"

libraryDependencies += "org.apache.deltaspike.core" % "deltaspike-core-impl" % DeltaspikeVersion

libraryDependencies += "org.apache.deltaspike.cdictrl" % "deltaspike-cdictrl-api" % DeltaspikeVersion

libraryDependencies += "org.apache.deltaspike.cdictrl" % "deltaspike-cdictrl-owb" % DeltaspikeVersion

// Expression Language

val JuelVersion = "2.2.7"

libraryDependencies += "de.odysseus.juel" % "juel-api" % JuelVersion

libraryDependencies += "de.odysseus.juel" % "juel-impl" % JuelVersion

libraryDependencies += "de.odysseus.juel" % "juel-spi" % JuelVersion

licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))

enablePlugins(BuildInfoPlugin)

buildInfoPackage := "com.sfxcode.sapphire.core"

buildInfoOptions += BuildInfoOption.BuildTime

val lastVersionString = "git tag -l".!!.split("\r?\n").last

version in Paradox := {
  if (isSnapshot.value)
    lastVersionString
  else version.value
}

paradoxProperties += ("app-version" -> {if (isSnapshot.value)
  lastVersionString
else version.value})

enablePlugins(ParadoxSitePlugin, ParadoxMaterialThemePlugin)
sourceDirectory in Paradox := sourceDirectory.value / "main" / "paradox"
ParadoxMaterialThemePlugin.paradoxMaterialThemeSettings(Paradox)

paradoxMaterialTheme in Paradox ~= {
  _.withRepository(uri("https://github.com/sfxcode/sapphire-core"))
}

// enablePlugins(SiteScaladocPlugin)

enablePlugins(GhpagesPlugin)

git.remoteRepo := "git@github.com:sfxcode/sapphire-core.git"
ghpagesNoJekyll := true


// publish

releaseCrossBuild := true

bintrayReleaseOnPublish in ThisBuild := true

publishMavenStyle := true

homepage := Some(url("https://github.com/sfxcode/sapphire-core"))

scmInfo := Some(
  ScmInfo(
    url("https://github.com/sfxcode/sapphire-core"),
    "scm:https://github.com/sfxcode/sapphire-core.git"
  )
)

developers := List(
  Developer(
    id    = "sfxcode",
    name  = "Tom Lamers",
    email = "tom@sfxcode.com",
    url   = url("https://github.com/sfxcode")
  )
)

