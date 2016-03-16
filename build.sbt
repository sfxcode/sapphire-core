name := "sapphire-core"

organization := "com.sfxcode.sapphire"

version := "1.1.1"

scalaVersion := "2.11.8"

scalacOptions += "-deprecation"

parallelExecution in Test := false

// (testOptions in Test) += Tests.Argument(TestFrameworks.Specs2, "html")

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

libraryDependencies += "org.specs2" %% "specs2-core" % "3.7.2" % "test"

libraryDependencies += "org.json4s" %% "json4s-native" % "3.3.0" % "test"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.6" % "test"

libraryDependencies += "com.storm-enroute" %% "scalameter" % "0.7" % "test"


scalacOptions in Test ++= Seq("-Yrangepos")

// Compile

// Environment

libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0"

libraryDependencies += "com.typesafe" % "config" % "1.3.0"

// UI

libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.60-R9"

libraryDependencies += "org.scalafx" %% "scalafxml-core-sfx8" % "0.2.2"

// CDI

libraryDependencies += "javax.enterprise" % "cdi-api" % "1.2"

libraryDependencies += "javax.annotation" % "javax.annotation-api" % "1.2"

libraryDependencies += "org.apache.openwebbeans" % "openwebbeans-impl" % "1.6.3"

libraryDependencies += "org.apache.deltaspike.core" % "deltaspike-core-impl" % "1.5.4"

libraryDependencies += "org.apache.deltaspike.cdictrl" % "deltaspike-cdictrl-owb" % "1.5.4"

libraryDependencies += "org.apache.geronimo.specs" % "geronimo-servlet_3.0_spec" % "1.0"

// Expression Language

libraryDependencies += "de.odysseus.juel" % "juel-api" % "2.2.7"

libraryDependencies += "de.odysseus.juel" % "juel-impl" % "2.2.7"

libraryDependencies += "de.odysseus.juel" % "juel-spi" % "2.2.7"

licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))

// developers += new Developer("tom.lamers", "Thomas Lamers", "tom@sfxcode.com", url("http://www.sfxcode.com"))

bintrayReleaseOnPublish in ThisBuild := false
