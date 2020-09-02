import sbt.url

import scala.xml.transform.{RewriteRule, RuleTransformer}
import scala.xml.{Comment, Elem, Node => XmlNode, NodeSeq => XmlNodeSeq}

name := "sapphire-core"

organization := "com.sfxcode.sapphire"

crossScalaVersions := Seq("2.13.3", "2.12.12")

scalaVersion := crossScalaVersions.value.head

compileOrder := CompileOrder.JavaThenScala

lazy val sapphire_core_root = Project(id = "sapphire-core", base = file("."))

javacOptions in test += "-Dorg.apache.deltaspike.ProjectStage=Test"

scalacOptions += "-deprecation"

parallelExecution in Test := false

val Json4sVersion  = "3.6.9"
val LogbackVersion = "1.2.3"

lazy val showcase = Project(id = "sapphire-core-showcase", base = file("demos/showcase"))
  .settings(
    scalaVersion := "2.13.2",
    name := "sapphire-core-showcase",
    description := "Sapphire Core Showcase",
    libraryDependencies ++= Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
          .map(m => "org.openjfx" % s"javafx-$m" % JavaFXVersion classifier osName),
    libraryDependencies += "ch.qos.logback" % "logback-classic" % LogbackVersion,
    resolvers += "sandec" at "https://sandec.bintray.com/repo",
    libraryDependencies += "com.sandec"          % "mdfx"          % "0.1.6",
    libraryDependencies += "com.jfoenix"         % "jfoenix"       % "9.0.10",
    libraryDependencies += "org.fxmisc.richtext" % "richtextfx"    % "0.10.3",
    libraryDependencies += "org.json4s"         %% "json4s-native" % Json4sVersion,
    mainClass := Some("com.sfxcode.sapphire.core.demo.showcse.Application")
  )
  .dependsOn(sapphire_core_root)

addCommandAlias("run-showcase", "sapphire-core-showcase/run")

lazy val demo_login = Project(id = "sapphire-login-demo", base = file("demos/login"))
  .settings(
    scalaVersion := "2.13.2",
    name := "sapphire-login-demo",
    description := "Sapphire Login Demo",
    libraryDependencies ++= Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
          .map(m => "org.openjfx" % s"javafx-$m" % JavaFXVersion classifier osName),
    libraryDependencies += "ch.qos.logback" % "logback-classic" % LogbackVersion,
    mainClass := Some("com.sfxcode.sapphire.core.demo.login.Application")
  )
  .dependsOn(sapphire_core_root)

addCommandAlias("run-login", "sapphire-login-demo/run")

lazy val demo_issues = Project(id = "sapphire-issues-demo", base = file("demos/issues"))
  .settings(
    scalaVersion := "2.13.2",
    name := "sapphire-issues-demo",
    description := "Sapphire Issues Demo",
    libraryDependencies ++= Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
          .map(m => "org.openjfx" % s"javafx-$m" % JavaFXVersion classifier osName),
    libraryDependencies += "ch.qos.logback" % "logback-classic" % LogbackVersion,
    libraryDependencies += "org.scalafx"   %% "scalafx"         % "14-R19",
    mainClass := Some("com.sfxcode.sapphire.core.demo.issues.Application")
  )
  .dependsOn(sapphire_core_root)

addCommandAlias("run-issues", "sapphire-issues-demo/run")

lazy val tutorial = Project(id = "sapphire-tutorial", base = file("demos/tutorial"))
  .settings(
    scalaVersion := "2.13.2",
    name := "sapphire-tutorial",
    description := "Sapphire Tutorial",
    libraryDependencies ++= Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
          .map(m => "org.openjfx" % s"javafx-$m" % JavaFXVersion classifier osName),
    libraryDependencies += "ch.qos.logback" % "logback-classic" % LogbackVersion,
    mainClass := Some("com.sfxcode.sapphire.core.demo.tutorial.Application")
  )
  .dependsOn(sapphire_core_root)

lazy val windows = Project(id = "sapphire-windows", base = file("demos/windows"))
  .settings(
    scalaVersion := "2.13.2",
    name := "sapphire-windows",
    description := "Sapphire Windows",
    libraryDependencies ++= Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
          .map(m => "org.openjfx" % s"javafx-$m" % JavaFXVersion classifier osName),
    libraryDependencies += "ch.qos.logback" % "logback-classic" % LogbackVersion,
    mainClass := Some("com.sfxcode.sapphire.core.demo.windows.Application")
  )
  .dependsOn(sapphire_core_root)

lazy val docs = (project in file("docs"))
  .enablePlugins(ParadoxSitePlugin)
  .enablePlugins(ParadoxMaterialThemePlugin)
  .enablePlugins(GhpagesPlugin)
  .settings(
    scalaVersion := "2.13.1",
    name := "sapphire core docs",
    publish / skip := true,
    ghpagesNoJekyll := true,
    git.remoteRepo := "git@github.com:sfxcode/sapphire-core.git",
    Compile / paradoxMaterialTheme ~= {
      _.withRepository(uri("https://github.com/sfxcode/sapphire-core"))

    },
    (Compile / paradoxMarkdownToHtml / excludeFilter) := (Compile / paradoxMarkdownToHtml / excludeFilter).value ||
          ParadoxPlugin.InDirectoryFilter((Compile / paradox / sourceDirectory).value / "includes")
  )

val JavaFXVersion = "14.0.2.1"

val osName = System.getProperty("os.name") match {
  case n if n.startsWith("Linux")   => "linux"
  case n if n.startsWith("Mac")     => "mac"
  case n if n.startsWith("Windows") => "win"
  case _                            => throw new Exception("Unknown platform!")
}

addCommandAlias("run-tutorial", "sapphire-tutorial/run")

// Test

libraryDependencies += "org.specs2" %% "specs2-core" % "4.10.3" % Test

libraryDependencies += "org.json4s" %% "json4s-native" % Json4sVersion % Test

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3" % Test

// showcase

resolvers += "sandec" at "https://sandec.bintray.com/repo"

libraryDependencies += "com.sandec" % "mdfx" % "0.1.6" % Provided

libraryDependencies += "com.jfoenix" % "jfoenix" % "9.0.10" % Provided

libraryDependencies += "org.fxmisc.richtext" % "richtextfx" % "0.10.5" % Provided

// Compile

libraryDependencies ++= Seq("base", "controls", "fxml", "graphics", "media", "swing", "web").map(m =>
  "org.openjfx" % s"javafx-$m" % JavaFXVersion % Provided classifier osName
)

// Environment

libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"

libraryDependencies += "com.typesafe" % "config" % "1.4.0"

// CDI

//libraryDependencies += "javax.enterprise" % "cdi-api" % "2.0"
//
//libraryDependencies += "javax.annotation" % "javax.annotation-api" % "1.3.2"
//
//libraryDependencies += "org.apache.openwebbeans" % "openwebbeans-impl" % "2.0.17"
//
//val DeltaspikeVersion = "1.9.4"
//
//libraryDependencies += "org.apache.deltaspike.core" % "deltaspike-core-impl" % DeltaspikeVersion
//
//libraryDependencies += "org.apache.deltaspike.cdictrl" % "deltaspike-cdictrl-api" % DeltaspikeVersion
//
//libraryDependencies += "org.apache.deltaspike.cdictrl" % "deltaspike-cdictrl-owb" % DeltaspikeVersion

// Expression Language

libraryDependencies += "org.apache.tomcat" % "tomcat-jasper-el" % "9.0.37"

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
    override def transform(node: XmlNode): XmlNodeSeq =
      node match {
        case e: Elem
            if e.label == "dependency" && e.child.exists(c => c.label == "scope" && c.text == "provided")
              && e.child.exists(c => c.label == "groupId" && c.text == "org.openjfx") =>
          val organization = e.child.filter(_.label == "groupId").flatMap(_.text).mkString
          val artifact     = e.child.filter(_.label == "artifactId").flatMap(_.text).mkString
          val version      = e.child.filter(_.label == "version").flatMap(_.text).mkString
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
    id = "sfxcode",
    name = "Tom Lamers",
    email = "tom@sfxcode.com",
    url = url("https://github.com/sfxcode")
  )
)

packageOptions += {
  Package.ManifestAttributes(
    "Created-By"               -> "Simple Build Tool",
    "Built-By"                 -> "sfxcode",
    "Build-Jdk"                -> System.getProperty("java.version"),
    "Specification-Title"      -> name.value,
    "Specification-Version"    -> version.value,
    "Specification-Vendor"     -> organization.value,
    "Implementation-Title"     -> name.value,
    "Implementation-Version"   -> version.value,
    "Implementation-Vendor-Id" -> organization.value,
    "Implementation-Vendor"    -> organization.value
  )
}
