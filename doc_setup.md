---
title: Setup
tags: [info]
keywords: CDI, Bindings,
last_updated: October 7, 2015
summary: "Project Setup."

---

## Create new project with build.sbt

Minimal SBT settings:

```scala

name := "project-name"

organization := "your.organization"

version := "1.0.2"

scalaVersion := "2.11.7"

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

scalacOptions += "-target:jvm-1.7"

libraryDependencies += "org.specs2" %% "specs2-core" % "3.6.4" % "test"

libraryDependencies +=   "com.sfxcode.sapphire" %% "sapphire-core" % "1.0.2"

```


## Checkout Blueprint Application
 
Base template code can be found under [sapphire-demo/blueprint](https://github.com/sfxcode/sapphire-demo/tree/master/blueprint).

## Update from console

```
sbt update

```



