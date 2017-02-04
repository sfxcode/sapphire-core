name := "sapphire-core"

organization := "com.sfxcode.sapphire"

version := "1.1.12"

crossScalaVersions := Seq( "2.12.1","2.11.8")

scalaVersion <<= crossScalaVersions { versions => versions.head }

scalacOptions += "-deprecation"

parallelExecution in Test := false

javacOptions ++= Seq("-source", "1.8")

javacOptions ++= Seq("-target", "1.8")

scalacOptions += "-target:jvm-1.8"

lazy val sapphire_core_root = Project(id = "sapphire-core", base = file(".")).
  configs(IntegrationTest).
  settings(Defaults.itSettings: _*)

// resolvers

resolvers += "sonatype-snapshots" at "http://oss.sonatype.org/content/repositories/snapshots"

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"


// Test

libraryDependencies += "org.specs2" %% "specs2-core" % "3.8.7" % "test"

libraryDependencies += "org.json4s" %% "json4s-native" % "3.5.0" % "test"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.9" % "test"

// Compile

// Environment

libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0"

libraryDependencies += "com.typesafe" % "config" % "1.3.1"

// UI

libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.102-R11"

// CDI

libraryDependencies += "javax.enterprise" % "cdi-api" % "1.2"

libraryDependencies += "javax.annotation" % "javax.annotation-api" % "1.3"

libraryDependencies += "org.apache.openwebbeans" % "openwebbeans-impl" % "1.7.1"

val DeltaspikeVersion = "1.7.2"

libraryDependencies += "org.apache.deltaspike.core" % "deltaspike-core-impl" % DeltaspikeVersion

libraryDependencies += "org.apache.deltaspike.cdictrl" % "deltaspike-cdictrl-api" % DeltaspikeVersion intransitive()

libraryDependencies += "org.apache.deltaspike.cdictrl" % "deltaspike-cdictrl-owb" % DeltaspikeVersion intransitive()

// Expression Language

libraryDependencies += "de.odysseus.juel" % "juel-api" % "2.2.7"

libraryDependencies += "de.odysseus.juel" % "juel-impl" % "2.2.7"

libraryDependencies += "de.odysseus.juel" % "juel-spi" % "2.2.7"

licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))

// developers += new Developer("tom.lamers", "Thomas Lamers", "tom@sfxcode.com", url("http://www.sfxcode.com"))

bintrayReleaseOnPublish in ThisBuild := false
