# Sapphire

A JavaFX Application Framework for Scala User.

## Documentation

Documentation under [https://sfxcode.github.io/sapphire-core](https://sfxcode.github.io/sapphire-core)

## Travis

[![Build Status](https://travis-ci.org/sfxcode/sapphire-core.svg?branch=master)](https://travis-ci.org/sfxcode/sapphire-core)

## Download

[ ![Download](https://api.bintray.com/packages/sfxcode/maven/sapphire-core/images/download.svg) ](https://bintray.com/sfxcode/maven/sapphire-core/_latestVersion)

## Giter8 Template

[Giter8 template](http://www.foundweekends.org/giter8/): [sapphire-sbt](https://github.com/sfxcode/sapphire-sbt.g8).

## Cross Build

Build and tested against Scala 2.12/2.13 and JDK 11/12

### Usage

g8 https://github.com/sfxcode/sapphire-sbt.g8

## Licence

Apache 2 License.


## Demos

### Tutorial

Simple JavaFX application with controller composition, controller exchange, ...

```
sbt run-tutorial
```

### Login

JavaFX Login demo enhanced by sapphire-core

```
sbt run-login
```

### Issues

spphire-core with ScalaFX and CDI (Deltaspike).

```
sbt run-issues
```

### Multiple Windows

usage of multiple window controller

### Showcase

Showcase Demo as visual reference for sapphire-core key concepts.


## Technology Stack

### Java  JDK 11/12

Sapphire runs agains the latest JDK 11/12 version.

### JavaFX

Java UI Application Framework as replacement for Swing.

Sapphire depends on OpenJFX 12

[https://openjfx.io](https://openjfx.io)

### Optional Dependency Injection (Issues Demo)

Sapphire use Apache Deltaspike as CDI Abstraction Layer (1.9.x).

[http://deltaspike.apache.org](http://deltaspike.apache.org)

The default implementation depends on Apache OpenWebBeans (2.0.x).

[http://openwebbeans.apache.org](http://openwebbeans.apache.org)

### Expression Language

Expressions are resolved by EL 3 [Tomcat Expression Language](https://tomcat.apache.org/tomcat-8.0-doc/elapi/index.html).

### ScalaFX (optional dependency)

A DSL for JavaFX written in Scala.

[ScalaFX Website](http://www.scalafx.org/)

ScalaFX plays very nice on top of sapphire-core applications.

ScalaFX support Scala versions up to 2.12.x

## Features

### Application Environment

- Application Controller
- UI Controller loading
- FXML Loading
- [Hot Reloding](https://sfxcode.github.io/sapphire-core/development.html)

### Bean Enhancement

- Every Java / Scala Bean can be used for [FXBean](https://sfxcode.github.io/sapphire-core/detail/fxbean.html)
- FXBean has additional support for java/scala Maps
- FXBean resolves Expressions on bean
- FXBean creates Properties needed for Binding on demand
- FXBean has change management by default

### Scala JavaFX Bean Binding

- Bindings by form id
- Binding with converter
- Adapter Pattern (FXBean Adapter)

### ViewController

- Controller Lifecycle
- Node Locator

### FXML Support

- Easy Connect FXML with ViewController
- Different FXML path options

## maven

sapphire-core is deployed on bintray (jcenter) and maven central.

## Supporters

JetBrains is supporting this open source project with:

[![Intellij IDEA](http://www.jetbrains.com/img/logos/logo_intellij_idea.png)](http://www.jetbrains.com/idea/)

