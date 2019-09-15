# ScalaFX

Until Version 1.5.x Sapphire depends on [ScalaFX](http://www.scalafx.org/), a DSL for JavaFX written in Scala.

Version 1.6.x and newer versions are built without direct ScalaFX
dependency, because ScalaFX (JavaFX UI DSL) is not basicly needed for
sapphire functionality. However, it can be used on top of this framework
as a very useful addition in writing JavaFX Applications.

Most of my own applications use scalafx because of a very useful DSL and
some nice wrapper functions around javafx with extended functionality
and / or the more scala friendly syntax.

## Documentation

Documentation can be found at [www.scalafx.org/docs/home/](http://www.scalafx.org/docs/home/)

Documentation is a little bit out of date, but there are also some other
places to find useful information.

* [Github](https://github.com/scalafx/scalafx)
* [Release Notes](https://github.com/scalafx/scalafx/blob/master/notes/12.0.2-R18.md)
* [Stackoverflow](https://stackoverflow.com/search?q=scalafx)
* [ProScalaFX](https://github.com/scalafx/ProScalaFX/)

## Usage

Include following snippet in your build.sbt file:

```
libraryDependencies += "org.scalafx" %% "scalafx" % "12.0.2-R18"
```

In your classes add scalafx Includes to your imports:

```
import scalafx.Includes._
```


