name := "project-name"

organization := "your.organization"

version := "1.0.0"

scalaVersion := "2.12.4"

resolvers += "sfxcode-bintray" at "https://dl.bintray.com/sfxcode/maven"

// Test

libraryDependencies += "org.specs2" %% "specs2-core" % "4.0.2" % "test"

// Compile

libraryDependencies += "com.sfxcode.sapphire" %% "sapphire-core" % "1.3.1"
