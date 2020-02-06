# Hot Reloading

Some tips and tricks to speed your development.

## DCEVM and HotSwapAgent

[DCEVM](http://dcevm.github.io/) together with [HotSwapAgent](http://hotswapagent.org/) can speedup your development. It enhance the ability of reloading of changed classes especially with scala.

## Hot Scene Reloading

Reloading can be used for:

* CSS
* FXML
* Bundle Content

### Setup

Create a new method in your ApplicationController:

```scala
 def reload(): Unit = {
    // Styling
    reloadStyles()
    // Resources
    applicationEnvironment.clearResourceBundleCache()
    applicationEnvironment.loadResourceBundle("bundles/application")
    // FXML
    val newMainViewController = getController[MainViewController]()
    replaceSceneContent(newMainViewController)
  }
```

Create a Button anywhere in your application and an corresponding action:

```scala
  def actionReload(event: ActionEvent) {
    getBean[ApplicationController]().reload()
  }
```

### IntelliJ

Very useful for compile on save is the [Save Actions Plugin](https://plugins.jetbrains.com/plugin/7642-save-actions)
