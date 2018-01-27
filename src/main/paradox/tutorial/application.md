# Tutorial Application

## Application object

A sapphire application must contain an Application object that extends FXApp.

applicationStage must be implemented.

@@snip [Application](../../../it/scala/com/sfxcode/sapphire/core/demo/appdemo/Application.scala) { #application }



## ApplicationController

Application controller is used for startup purposes.

Normally the main scene content is replaced by a ViewController.

Here we will use a MainWindowController and later we connect a Navigation-, Workspace- and StatusBarController.

@@snip [ApplicationController](../../../it/scala/com/sfxcode/sapphire/core/demo/appdemo/Application.scala) { #applicationController }

## MainWindowController

The following code snippet loads the MainWindowController by the [FXMLoader](https://github.com/sfxcode/sapphire-core/blob/master/src/main/scala/com/sfxcode/sapphire/core/fxml/FxmlLoading.scala) from the CDI managed ApplicationController Bean.

```scala
  def mainWindowController = getController[MainWindowController]()

```

This pattern for Controller-Loading is commonly used in sapphire-core Framework.
