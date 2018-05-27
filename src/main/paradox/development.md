# Development Setup

Some tips and tricks.

## DCEVM and HotSwapAgent

[DCEVM](http://dcevm.github.io/) together with [HotSwapAgent](http://hotswapagent.org/) can speedup your development. It enhance the ability of reloading of changed classes especially with scala.

### IntelliJ Plugin

An IntelliJ Plugin can be found at [hotswap-agent-intellij-plugin](https://github.com/dmitry-zhuravlev/hotswap-agent-intellij-plugin).


## Hot Scene Reloading

### Setup

Create a new method in your ApplicationController:

```scala
  def replacePrimarySceneContent(): Unit = {
    val newMainWindowController = getController[MainWindowController]()
    replaceSceneContent(newMainWindowController)
  }
```

Create a Button anywhere in your application and an corresponding action:

```scala
  def actionReload(event: ActionEvent) {
    getBean[ApplicationController]().replacePrimarySceneContent()
  }
```