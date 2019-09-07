import sbt.url

import scala.xml.transform.{RewriteRule, RuleTransformer}
import scala.xml.{Comment, Elem, Node => XmlNode, NodeSeq => XmlNodeSeq}

name := "sapphire-core"

organization := "com.sfxcode.sapphire"

crossScalaVersions := Seq("2.12.9", "2.13.0")

scalaVersion := crossScalaVersions.value.head

val JavaFXVersion = "12.0.2"

val osName = System.getProperty("os.name") match {
  case n if n.startsWith("Linux") => "linux"
  case n if n.startsWith("Mac") => "mac"
  case n if n.startsWith("Windows") => "win"
  case _ => throw new Exception("Unknown platform!")
}

javacOptions in test += "-Dorg.apache.deltaspike.ProjectStage=Test"


scalacOptions += "-deprecation"

parallelExecution in Test := false

lazy val sapphire_core_root = Project(id = "sapphire-core", base = file("."))


lazy val demo_login = Project(id = "sapphire-login-demo",base = file("demos/login")).settings(
  name:= "sapphire-login-demo",
  description := "Sapphire Login Demo",
  libraryDependencies ++= Seq("base", "controls", "fxml", "graphics", "media", "swing", "web").map(
    m => "org.openjfx" % s"javafx-$m" % JavaFXVersion classifier osName),
  libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3",
  mainClass := Some("com.sfxcode.sapphire.core.demo.login.Application")

).dependsOn(sapphire_core_root)

addCommandAlias("run-login", "sapphire-login-demo/run")

lazy val demo_issues = Project(id = "sapphire-issues-demo",base = file("demos/issues")).settings(
  name:= "sapphire-issues-demo",
  description := "Sapphire Issues Demo",
  libraryDependencies ++= Seq("base", "controls", "fxml", "graphics", "media", "swing", "web").map(
    m => "org.openjfx" % s"javafx-$m" % JavaFXVersion classifier osName),
  libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3",
  mainClass := Some("com.sfxcode.sapphire.core.demo.issues.Application")

).dependsOn(sapphire_core_root)

addCommandAlias("run-issues", "sapphire-issues-demo/run")


lazy val tutorial = Project(id = "sapphire-tutorial",base = file("demos/tutorial")).settings(
  name:= "sapphire-tutorial",
  description := "Sapphire Tutorial",
  libraryDependencies ++= Seq("base", "controls", "fxml", "graphics", "media", "swing", "web").map(
    m => "org.openjfx" % s"javafx-$m" % JavaFXVersion classifier osName),
  libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3",
  mainClass := Some("com.sfxcode.sapphire.core.demo.tutorial.Application")

).dependsOn(sapphire_core_root)

lazy val docs = (project in file("docs"))
  .enablePlugins(ParadoxSitePlugin)
  .enablePlugins(ParadoxMaterialThemePlugin)
  .enablePlugins(GhpagesPlugin)
  .settings(
    name := "sapphire core docs",
    publish / skip := true,
    ghpagesNoJekyll := true,
    git.remoteRepo := "git@github.com:sfxcode/sapphire-core.git",
    Compile / paradoxMaterialTheme ~= {
      _.withRepository(uri("https://github.com/sfxcode/sapphire-core"))

    }
  )

addCommandAlias("run-tutorial", "sapphire-tutorial/run")

// resolvers

resolvers += "sonatype-snapshots" at "http://oss.sonatype.org/content/repositories/snapshots"


// Test

libraryDependencies += "org.specs2" %% "specs2-core" % "4.7.0" % Test

libraryDependencies += "org.json4s" %% "json4s-native" % "3.6.7" % Test

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3" % Test

// Compile



libraryDependencies ++= Seq("base", "controls", "fxml", "graphics", "media", "swing", "web").map(
  m => "org.openjfx" % s"javafx-$m" % JavaFXVersion % Provided classifier osName)

// Environment


libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"

libraryDependencies += "com.typesafe" % "config" % "1.3.4"

// CDI

libraryDependencies += "javax.enterprise" % "cdi-api" % "2.0"

libraryDependencies += "javax.annotation" % "javax.annotation-api" % "1.3.2"

libraryDependencies += "org.apache.openwebbeans" % "openwebbeans-impl" % "2.0.11"

val DeltaspikeVersion = "1.9.1"

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



// publish

// Use `pomPostProcess` to remove dependencies marked as "provided" from publishing in POM
// This is to avoid dependency on wrong OS version JavaFX libraries [Issue #289]
// See also [https://stackoverflow.com/questions/27835740/sbt-exclude-certain-dependency-only-during-publish]

pomPostProcess := { node: XmlNode =>
  new RuleTransformer(new RewriteRule {
    override def transform(node: XmlNode): XmlNodeSeq = node match {
      case e: Elem if e.label == "dependency" && e.child.exists(c => c.label == "scope" && c.text == "provided")
        && e.child.exists(c => c.label == "groupId" && c.text == "org.openjfx")=>
        val organization = e.child.filter(_.label == "groupId").flatMap(_.text).mkString
        val artifact = e.child.filter(_.label == "artifactId").flatMap(_.text).mkString
        val version = e.child.filter(_.label == "version").flatMap(_.text).mkString
        Comment(s"provided dependency $organization#$artifact;$version has been omitted")
      case _ => node
    }
  }).transform(node).head
}

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

packageOptions += {
  Package.ManifestAttributes(
    "Created-By" -> "Simple Build Tool",
    "Built-By" -> "sfxcode",
    "Build-Jdk" -> System.getProperty("java.version"),
    "Specification-Title" -> name.value,
    "Specification-Version" -> version.value,
    "Specification-Vendor" -> organization.value,
    "Implementation-Title" -> name.value,
    "Implementation-Version" -> version.value,
    "Implementation-Vendor-Id" -> organization.value,
    "Implementation-Vendor" -> organization.value
  )
}

