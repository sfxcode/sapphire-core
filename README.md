# Sapphire

A JavaFX Application Framework for Scala User.

## Documentation

Documentation under [https://sfxcode.github.io/sapphire-core](https://sfxcode.github.io/sapphire-core)


## Travis

[![Build Status](https://travis-ci.org/sfxcode/sapphire-core.svg?branch=master)](https://travis-ci.org/sfxcode/sapphire-core)

## Download

[ ![Download](https://api.bintray.com/packages/sfxcode/maven/sapphire-core/images/download.svg) ](https://bintray.com/sfxcode/maven/sapphire-core/_latestVersion)


## Demos

Demos can be found under [sapphire-demo](http://sfxcode.github.io/sapphire-demo/) on github.

## Template

[Giter8 template](http://www.foundweekends.org/giter8/): [sapphire-sbt](https://github.com/sfxcode/sapphire-sbt.g8).

### Usage

g8 https://github.com/sfxcode/sapphire-sbt.g8



## Licence

Apache 2 License.


### Demo

Simple Demo can be found in the it directory.

Start in SBT Console:

```
it:runMain com.sfxcode.sapphire.core.demo.appdemo.DemoApplication
```

## Technology Stack

### Java  JDK 11/12

Sapphire runs agains the latest JDK 11/12 version.

### JavaFX

Java UI Application Framework as replacement for Swing.

Sapphire depends on OpenJFX 12

[https://openjfx.io](https://openjfx.io)


### ScalaFX

A DSL for JavaFX written in Scala.

[ScalaFX Website](http://www.scalafx.org/)


### Dependency Injection

Sapphire use Apache Deltaspike as CDI Abstraction Layer (1.9.x).

[http://deltaspike.apache.org](http://deltaspike.apache.org)

The default implementation depends on Apache OpenWebBeans (2.0.x).

[http://openwebbeans.apache.org](http://openwebbeans.apache.org)

### Expression Language

Expressions are resolved by JUEL.

[http://juel.sourceforge.net](http://juel.sourceforge.net)

## Features

### Application Environment powered by CDI

- Application Controller
- UI Controller loading
- Parameter Provider
- Configuration Provider
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
- Controller CDI Additions

### FXML Support

- Simple load from controller


## maven

sapphire-core is deployed on bintray (jcenter).

