name := "project-name"

organization := "your.organization"

version := "1.0.0"

scalaVersion := "2.12.8"

resolvers += "sfxcode-bintray" at "https://dl.bintray.com/sfxcode/maven"

// Test

libraryDependencies += "org.specs2" %% "specs2-core" % "4.3.5" % "test"

// Compile

libraryDependencies += "com.sfxcode.sapphire" %% "sapphire-core" % "1.4.2"
