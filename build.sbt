name := "sapphire-core"

organization := "com.sfxcode.sapphire"

scalaVersion := "2.12.4"

scalacOptions += "-deprecation"

parallelExecution in Test := false


lazy val sapphire_core_root = Project(id = "sapphire-core", base = file(".")).
  configs(IntegrationTest).
  settings(Defaults.itSettings: _*)

// resolvers

resolvers += "sonatype-snapshots" at "http://oss.sonatype.org/content/repositories/snapshots"

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"


// Test

libraryDependencies += "org.specs2" %% "specs2-core" % "4.0.2" % "test"

libraryDependencies += "org.json4s" %% "json4s-native" % "3.5.3" % "test"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3" % "test"

// Compile

// Environment

libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2"

libraryDependencies += "com.typesafe" % "config" % "1.3.2"

// UI

libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.144-R12"

// CDI

libraryDependencies += "javax.enterprise" % "cdi-api" % "2.0"

libraryDependencies += "javax.annotation" % "javax.annotation-api" % "1.3.1"

libraryDependencies += "org.apache.openwebbeans" % "openwebbeans-impl" % "2.0.2"

val DeltaspikeVersion = "1.8.0"

libraryDependencies += "org.apache.deltaspike.core" % "deltaspike-core-impl" % DeltaspikeVersion

libraryDependencies += "org.apache.deltaspike.cdictrl" % "deltaspike-cdictrl-api" % DeltaspikeVersion intransitive()

libraryDependencies += "org.apache.deltaspike.cdictrl" % "deltaspike-cdictrl-owb" % DeltaspikeVersion intransitive()

// Expression Language

val JuelVersion = "2.2.7"

libraryDependencies += "de.odysseus.juel" % "juel-api" % JuelVersion

libraryDependencies += "de.odysseus.juel" % "juel-impl" % JuelVersion

libraryDependencies += "de.odysseus.juel" % "juel-spi" % JuelVersion

licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))

bintrayReleaseOnPublish in ThisBuild := false

enablePlugins(BuildInfoPlugin)

buildInfoPackage := "com.sfxcode.sapphire.core"

buildInfoOptions += BuildInfoOption.BuildTime
