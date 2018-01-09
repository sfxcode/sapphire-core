---
title: Create first App
tags: [info, tutorial]
keywords: CDI, ApplicationController,
last_updated: October 7, 2015
summary: "Step by step tutorial - Application"

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

Normally the main scene content is replaced by a ViewController.

Here we will use a MainWindowController with Navigation-, Workspace- and StatusBarController.



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

## Controller loading

The following code snippet is used to create an instance of the MainWindowController by demand.

```scala

lazy val mainWindowController = getController[MainWindowController]()

```

The getController method create a controller instance by using a .fxml file.
In this case the name of the fxml file is MainWindow.fxml. You can find it under

```
src/main/resources/fxml/MainWindow.fxml
```

```xml

<AnchorPane maxHeight="-Infinity" maxWidth="-Infinity" minHeight="400"
minWidth="-Infinity" prefHeight="600.0" prefWidth="800.0"
xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1"
fx:controller="com.sfxcode.sapphire.core.demo.blueprint.controller.MainWindowController">
    <children>
        <BorderPane prefHeight="600.0" prefWidth="600.0"
        AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
            <top>
                <AnchorPane fx:id="navigationPane" maxHeight="1.7976931348623157E308"
                maxWidth="1.7976931348623157E308" prefHeight="40.0" styleClass="navigation" />
            </top>
            <center>
                <AnchorPane fx:id="workspacePane" maxHeight="1.7976931348623157E308"
                maxWidth="1.7976931348623157E308" prefHeight="200.0" />
            </center>
            <bottom>
                <AnchorPane fx:id="statusPane" maxHeight="1.7976931348623157E308"
                maxWidth="1.7976931348623157E308" prefHeight="30.0" styleClass="status" />
            </bottom>
        </BorderPane>
    </children>
    <stylesheets>
        <URL value="@blueprint.css" />
    </stylesheets>
</AnchorPane>
```


The root of the mainWindowController instance is bound to the root of the fxml document.




... to be continued !!!

 


