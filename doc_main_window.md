---
title: MainWindowController
tags: [info, tutorial]
keywords: CDI, MainWindowController, ContentManager
last_updated: October 7, 2015
summary: "Step by step tutorial - MainWindowController"

---
## MainWindowController

The MainWindowController is used for managing the base Layout-Parts of the application.

* NavigationController (top)
* WorkspaceController (middle)
* StatusBarController (bottom)

```scala

class MainWindowController extends ViewController with LazyLogging{

  lazy val workspaceController = getController[WorkspaceController]()
  lazy val personController = getController[PersonController]()
  lazy val navigationController = getController[NavigationController]()
  lazy val statusBarController = getBean[StatusBarController]()

  @FXML
  var workspacePane: Pane = _
  @FXML
  var statusPane: Pane = _
  @FXML
  var navigationPane: Pane = _

  var workspaceManager:ContentManager = _
  var navigationManager:ContentManager = _
  var statusBarManager:ContentManager = _

  ...

}
```

## ContentManager

For each of the panes a ContentManager is used for controlling the content of the Pane elements.


```scala

  override def didGainVisibilityFirstTime() {
    navigationManager = ContentManager(navigationPane, this, navigationController)
    statusBarManager = ContentManager(statusPane, this, statusBarController)
    workspaceManager = ContentManager(workspacePane, this, workspaceController)
  }

```

In this sample app we initialize the content manager with the pane, the parent controller (this) and an intial view controller.

The didGainVisibilityFirstTime method is part of the ViewController lifecycle and is executed only once.


... to be continued !!!

 


