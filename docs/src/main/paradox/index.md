# Sapphire Core

A JavaFX  Application Framework for Scala User. It combines scala programming patterns with MVC for building complex JavaFX Applications.

## Cross Build

Build and tested against Scala 2.12/2.13 and JDK 11/12

## Frameworks


### JavaFX

Java UI Application Framework as replacement for Swing.

Sapphire depends on OpenJFX 11/12.

[https://openjfx.io](https://openjfx.io)



### Dependency Injection

Sapphire use [Apache Deltaspike](http://deltaspike.apache.org) as CDI Abstraction Layer (1.9.x).

The default [CDI](https://de.wikipedia.org/wiki/Contexts_and_Dependency_Injection) implementation depends on [Apache OpenWebBeans](http://openwebbeans.apache.org) (2.0.x).

### Expression Language

Expressions are resolved by [JUEL](http://juel.sourceforge.net).

### ScalaFX

Until Version 1.5.x Sapphire depends on [ScalaFX](http://www.scalafx.org/), a DSL for JavaFX written in Scala.

Version 1.6.x and newer versions are built without ScalaFX dependency, because ScalaFX (as a wrapper library) is not really needed for sapphire functionality.
However, it can be used on top of this framework when it is useful.


## Maven

Sapphire is published to Bintray and linked to Maven Central.

### Repository

```
resolvers += "sfxcode-bintray" at "https://dl.bintray.com/sfxcode/maven"

```

### Artifact

@@dependency[sbt,Maven,Gradle] {
  group="com.sfxcode.sapphire"
  artifact="sapphire-core_2.12"
  version="$app-version$"
}

## Getting started

### Setup Base Application with Giter8

```
g8 https://github.com/sfxcode/sapphire-sbt.g8
```

### Demos

Explore demos and Tutorial in project demo directory.

## Licence

[Apache 2](https://github.com/sfxcode/sapphire-core/blob/master/LICENSE)

@@@ index

 - [Features](features.md)
 - [Development](development/index.md)
 - [Tutorial](tutorial/index.md)
 - [Core Concepts](detail/index.md)
 - [Examples](sample/index.md)
 - [Extension](extension.md)
 - [Changes ](changes.md)


@@@
