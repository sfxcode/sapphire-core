---
title: Create first App
tags: [info]
keywords: CDI, ApplicationController,
last_updated: October 7, 2015
summary: "Step by step tutorial."

---
## Setup Project

Setup the project as described before.

## Create Application object

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

## Create ApplicationController

```scala

@Named
@ApplicationScoped
class ApplicationController extends AppController {

  def applicationDidLaunch() {
    logger.info("start " + this)
  }
}

```

... to be continued

 


