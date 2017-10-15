# Sapphire

A JavaFX Application Framework for Scala User.

## Version

Scala Version is 2.12.3 / 2.11.11.

## Travis

[![Build Status](https://travis-ci.org/sfxcode/sapphire-core.svg?branch=master)](https://travis-ci.org/sfxcode/sapphire-core)

## Download

[ ![Download](https://api.bintray.com/packages/sfxcode/maven/sapphire-core/images/download.svg) ](https://bintray.com/sfxcode/maven/sapphire-core/_latestVersion)

## Licence

Apache 2 License.

## Documentation

Documentation can be found at [http://sfxcode.github.io/sapphire-core/](http://sfxcode.github.io/sapphire-core/)

## Technology Stack

### Java / JDK 1.8 u144

Sapphire runs agains the latest JDK 8 version.

### JavaFX

Java UI Application Framework as replacement for Swing.

Sapphire depends on JavaFX 8, which is included in JDK 8.

[http://www.oracle.com/technetwork/java/javafx/overview/index.html](http://www.oracle.com/technetwork/java/javafx/overview/index.html)


### ScalaFX

A DSL for JavaFX written in Scala.

[https://code.google.com/p/scalafx/](https://code.google.com/p/scalafx)


### Dependency Injection

Sapphire use Apache Deltaspike as CDI Abstraction Layer (1.8.0).

[http://deltaspike.apache.org](http://deltaspike.apache.org)

The default implementation depends on Apache OpenWebBeans (2.0.0).

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

### Bean Enhancement

- Every Java / Scala Bean can be used for FXBean
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

### TODO

- Documentation !
- g8 template
- automatic binding for more node types (currently: Label, TextField, TextArea, CheckBox)


## maven

sapphire-core is deployed on bintray (jcenter).


## Demos

Demos can be found under [sapphire-demo](http://sfxcode.github.io/sapphire-demo/) on github.
