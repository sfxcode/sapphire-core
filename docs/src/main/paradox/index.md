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
  version="$project.version$"
}

### Demos

Explore demos and Tutorial in project demo directory.

## Licence

[Apache 2](https://github.com/sfxcode/sapphire-core/blob/master/LICENSE)

@@@ index

 - [Features](features.md)
 - [getting_started](getting_started.md)
 - [Development](development/index.md)
 - [Tutorial](tutorial/index.md)
 - [Core Concepts](detail/index.md)
 - [Examples](sample/index.md)
 - [Extension](extension.md)
 - [ScalaFX](scalafx.md)
 - [Changes ](changes.md)


@@@
