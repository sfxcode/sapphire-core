# Sapphire

A JavaFX Application Framework for Scala User.

## Version

Actual Version is 0.8.0.

Snapshot Version = 0.8.1-SNAPSHOT

## Licence

Apache 2 License.

## Technology Stack

### Java / JDK 1.8

Sapphire runs agains the latest JDK 1.8 EAP version.

### JavaFX

Java UI Application Framework as replacement for Swing.

Sapphire depends on JavaFX 8.

[http://www.oracle.com/technetwork/java/javafx/overview/index.html](http://www.oracle.com/technetwork/java/javafx/overview/index.html)


### ScalaFX

A DSL for JavaFX written in Scala.

[https://code.google.com/p/scalafx/](https://code.google.com/p/scalafx)

### Dependency Injection

Sapphire use Apache Deltaspike as CDI Abstraction Layer.

[http://deltaspike.apache.org](http://deltaspike.apache.org)

The default implementation depends on Apache OpenWebBeans.

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
- Value Factory for using expressions inside fxml

### TODO

- Documentation
- g8 template
- automatic binding for more node types (currently: Label, TextField, TextArea, CheckBox)


## maven

Sapphire use a github repository for maven.

[https://github.com/sfxcode/mvn-repo](https://github.com/sfxcode/mvn-repo)

### sbt build snippet

```scala

resolvers ++= Seq(
  "sfxcode-releases" at "https://github.com/sfxcode/mvn-repo/raw/master/releases",
  "sfxcode-snapshots" at "https://github.com/sfxcode/mvn-repo/raw/master/snapshots"
)

libraryDependencies += "com.sfxcode.sapphire" %% "sapphire-core" % "0.8.0"
```

## Demos

Demos can be found under [https://github.com/sfxcode/sapphire-demo](sapphire-demo) on github.
