name := "sapphire-core"

organization := "com.sfxcode.sapphire"

version := "0.9.9.4"

scalaVersion := "2.11.5"

scalacOptions += "-deprecation"

// parallelExecution in Test := false

// (testOptions in Test) += Tests.Argument(TestFrameworks.Specs2, "html")

javacOptions ++= Seq("-source", "1.8")

javacOptions ++= Seq("-target", "1.8")

scalacOptions += "-target:jvm-1.7"


// resolvers

resolvers ++= Seq("snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
                    "releases"  at "http://oss.sonatype.org/content/repositories/releases",
                    "java"  at "https://maven.java.net/content/groups/public/",
                      "apache" at "http://maven.acm-sl.org/artifactory/list/libs-releases"
)

resolvers += "jboss" at "https://repository.jboss.org/nexus/content/repositories/"

resolvers += "lodgon" at "http://maven.lodgon.com/maven2/"

resolvers ++= Seq(
  "sfxcode-releases" at "https://raw.github.com/sfxcode/mvn-repo/master/releases",
  "sfxcode-snapshots" at "https://raw.github.com/sfxcode/mvn-repo/master/snapshots"
)

resolvers ++= Seq("snapshots", "releases").map(Resolver.sonatypeRepo)

// Test

libraryDependencies += "org.specs2" %% "specs2" % "2.4.15" % "test"

libraryDependencies += "org.json4s" %% "json4s-native" % "3.2.11" % "test"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.2" % "test"


scalacOptions in Test ++= Seq("-Yrangepos")

// Compile

// Environment

libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0"

libraryDependencies += "com.typesafe" % "config" % "1.2.1"

// UI

//libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.20-R6"

libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.20-R7-SNAPSHOT"

// CDI

libraryDependencies += "javax.enterprise" % "cdi-api" % "1.2"

libraryDependencies += "org.apache.openwebbeans" % "openwebbeans-impl" % "1.2.7"

libraryDependencies += "org.apache.deltaspike.core" % "deltaspike-core-impl" % "1.2.1"

libraryDependencies += "org.apache.deltaspike.cdictrl" % "deltaspike-cdictrl-owb" % "1.2.1"

libraryDependencies += "org.apache.geronimo.specs" % "geronimo-servlet_3.0_spec" % "1.0"

// Expression Language

libraryDependencies += "de.odysseus.juel" % "juel-api" % "2.2.7"

libraryDependencies += "de.odysseus.juel" % "juel-impl" % "2.2.7"

libraryDependencies += "de.odysseus.juel" % "juel-spi" % "2.2.7"

// unmanagedJars in Compile += Attributed.blank(file(System.getenv("JAVA_HOME") + "/jre/lib/ext/jfxrt.jar"))

publishTo := {
  if (version.value.trim.endsWith("SNAPSHOT"))
    Some(Resolver.file("file", new File("/Users/tom/projects/sfxcode/mvn-repo/snapshots")))
  else
    Some(Resolver.file("file", new File("/Users/tom/projects/sfxcode/mvn-repo/releases")))
}




// dependency

net.virtualvoid.sbt.graph.Plugin.graphSettings


