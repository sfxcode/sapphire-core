# Sapphire Core

A JavaFX (ScalaFX) Application Framework for Scala User. It combines scala programming patterns with MVC for building complex JavaFX Applications.

## Frameworks

### JavaFX

Java UI Application Framework as replacement for Swing.

Sapphire depends on JavaFX 8, which is included in JDK 8.

[http://www.oracle.com/technetwork/java/javafx/overview/index.html](http://www.oracle.com/technetwork/java/javafx/overview/index.html)


### ScalaFX

Sapphire depends on  [ScalaFX](http://www.scalafx.org/), A DSL for JavaFX written in Scala.


### Dependency Injection

Sapphire use [Apache Deltaspike](http://deltaspike.apache.org) as CDI Abstraction Layer (1.8.x).

The default [CDI](https://de.wikipedia.org/wiki/Contexts_and_Dependency_Injection) implementation depends on [Apache OpenWebBeans](http://openwebbeans.apache.org) (2.0.x).

### Expression Language

Expressions are resolved by [JUEL](http://juel.sourceforge.net).

## Setup

### Repository

```
resolvers += "sfxcode-bintray" at "https://dl.bintray.com/sfxcode/maven"

```

### Artifact

@@dependency[sbt,Maven,Gradle] {
  group="com.sfxcode.nosql"
  artifact="sapphire-core_2.12"
  version="$app-version$"
}

## Licence

[Apache 2](https://github.com/sfxcode/sapphire-core/blob/master/LICENSE)

@@@ index

 - [Features](features.md)
 - [Development Setup](development.md)
 - [Development Tools](tools.md)
 - [Tutorial Setup](tutorial/setup.md)
 - [Tutorial Application](tutorial/application.md)
 - [Tutorial MainWindowController](tutorial/main_window.md)
 - [Tutorial Navigation](tutorial/navigation.md)
 - [In Depth FXBean](detail/fxbean.md)
 - [In Depth FxmlLoading](detail/fxml_loading.md)
 - [In Depth ViewController](detail/view_controller.md)
 - [Extension](extension.md)
 - [Example Basic](sample/basic.md)
 - [Example Extended](sample/advanced.md)
 - [Changes ](changes.md)





@@@
