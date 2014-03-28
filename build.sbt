name := "sapphire-core"

organization := "com.sfxcode.sapphire"

version := "0.8.1"

scalaVersion := "2.10.3"

scalacOptions += "-deprecation"

parallelExecution in Test := false

// (testOptions in Test) += Tests.Argument(TestFrameworks.Specs2, "html")

// resolvers

resolvers ++= Seq("snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
                    "releases"  at "http://oss.sonatype.org/content/repositories/releases",
                    "java"  at "https://maven.java.net/content/groups/public/",
                      "apache" at "http://maven.acm-sl.org/artifactory/list/libs-releases"
)

resolvers += "jboss" at "https://repository.jboss.org/nexus/content/repositories/"

resolvers += "lodgon" at "http://maven.lodgon.com/maven2/"

// Test

libraryDependencies += "org.specs2" %% "specs2" % "2.3.7" % "test"

// Compile

// Environment

libraryDependencies += "com.typesafe" %% "scalalogging-slf4j" % "1.0.1"

libraryDependencies += "com.typesafe" % "config" % "1.0.2"

// UI

libraryDependencies += "org.scalafx" % "scalafx_2.10" % "8.0.0-R4"

// CDI

libraryDependencies += "org.apache.openejb" % "javaee-api" % "6.0-4"

libraryDependencies += "org.apache.deltaspike.core" % "deltaspike-core-api" % "0.6"

libraryDependencies += "org.apache.deltaspike.core" % "deltaspike-core-impl" % "0.6"

libraryDependencies += "org.apache.deltaspike.cdictrl" % "deltaspike-cdictrl-api" % "0.6"

libraryDependencies += "org.apache.deltaspike.cdictrl" % "deltaspike-cdictrl-owb" % "0.6"

libraryDependencies += "org.apache.openwebbeans" % "openwebbeans-impl" % "1.2.2"

libraryDependencies += "org.apache.openwebbeans" % "openwebbeans-spi" % "1.2.2"

// Expression Language

libraryDependencies += "de.odysseus.juel" % "juel-impl" % "2.2.6"

libraryDependencies += "de.odysseus.juel" % "juel-spi" % "2.2.6"


publishTo := {
  if (version.value.trim.endsWith("SNAPSHOT"))
    Some(Resolver.file("file", new File("/Users/admin/projects/sfxcode/mvn-repo/snapshots")))
  else
    Some(Resolver.file("file", new File("/Users/admin/projects/sfxcode/mvn-repo/releases")))
}

