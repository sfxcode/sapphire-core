---
title: Create first App
tags: [info]
keywords: CDI, ApplicationController,
last_updated: October 7, 2015
summary: "Step by step tutorial."

---
## Setup Project

Setup the project as described before.

## Application object

A sapphire application must contain an Application object that extends FXApp.

applicationStage must be implemented.

```scala

object MyApplication extends FXApp {
  
  JFXApp.AUTO_SHOW = true
  
  override def applicationStage:Stage  = new PrimaryStage {
    title = "My new App"
    scene = new Scene {
    }
  }
}

```

## ApplicationController

Application controller is used for startup purposes.

Normally the main scene is replaced by a ViewController.

Here we will use a MainWindowController with Navigation, Workspace and StatusBarConroller.



```scala

@Named
@ApplicationScoped
class ApplicationController extends AppController {

  lazy val mainWindowController = getController[MainWindowController]()

  def applicationDidLaunch() {
    logger.info("start " + this)
    replaceSceneContent(mainWindowController)
  }

}

```



... to be continued

 


