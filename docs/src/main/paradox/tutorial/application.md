# Tutorial Application

## Application object

A sapphire application must contain an Application object that extends BaseApplication.


@@snip [Application](../../../../../demos/tutorial/src/main/scala/com/sfxcode/sapphire/core/demo/tutorial/Application.scala)


## ApplicationController

Application controller is used for startup purposes.

Normally the main scene content is replaced by a ViewController.

Here we will use a MainViewController and later we connect a Navigation-, Workspace- and StatusBarController.

@@snip [ApplicationController](../../../../../demos/tutorial/src/main/scala/com/sfxcode/sapphire/core/demo/tutorial/ApplicationController.scala)

## MainViewController

The following code snippet loads the MainViewController by the [FXMLoader](https://github.com/sfxcode/sapphire-core/blob/master/src/main/scala/com/sfxcode/sapphire/core/fxml/FxmlLoading.scala) from the CDI managed ApplicationController Bean.

```scala
  lazy val mainViewController = getController[MainViewController]()

```

This pattern for Controller-Loading is commonly used in sapphire-core Framework.
