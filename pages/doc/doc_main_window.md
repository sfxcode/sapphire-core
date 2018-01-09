---
title: MainWindowController
tags: [info, tutorial]
keywords: CDI, MainWindowController, ViewController, ContentManager
last_updated: October 7, 2015
summary: "Step by step tutorial - ViewController"

---

## ViewController

ViewController is the base class for all Controller used by the sapphire framework.

ViewController points to the root pane of the corresponding ui node.

 A ViewController can be instantiated by a FXML File and his ClassName or by manually creating an instance.

## MainWindowController

The MainWindowController is used for managing the base Layout-Parts of the application.
All Controller of these Parts also subclasses ViewController.

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

ContentManager are primary used as container for a ViewController, which can easily be replaced by an other controller.


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

 


