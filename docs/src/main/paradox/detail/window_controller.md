# WindowController

@@include[window_controller_text](../includes/window_controller_text.md)


```scala
class ApplicationController extends BaseApplicationController {
  // Content here
}
```

WindowController height and width are 800 x 600 by default, but can be easily overwritten in implementation classes:

```scala
  // overwrite window default size
  override def width: Int = 200
  override def height: Int = 400

```

## Features

- Lifecycle

## BaseApplicationController

BaseApplicationController is normally the base class for your ApplicationController.

Initialization is automatically done by the Application class (must be an object that extends BaseApplication) to be runnable

### Lifecycle Methods

Following methods are supported:

* applicationWillLaunch (before setting stage)
* applicationDidLaunch (after setting stage)
* applicationWillStop (before Appliction terminates)

### Application

Sample Code:

@@snip [Application.scala](../../../../../demos/issues/src/main/scala/com/sfxcode/sapphire/core/demo/issues/Application.scala) { #Application }

BaseApplication Code:

@@snip [BaseApplication.scala](../../../../../src/main/scala/com/sfxcode/sapphire/core/application/BaseApplication.scala) { #BaseApplication }

### ApplicationController

Base class for your Application (extends normally DefaultWindowController). Is is used for setting the content view and holds application wide information.

Sample Code:

@@snip [Application.scala](../../../../../demos/issues/src/main/scala/com/sfxcode/sapphire/core/demo/issues/Application.scala) { #ApplicationController }

##  AdditionalWindowController

Multiple Windows are supported by extending AdditionalWindowController.

Example Usage:

### Create a CDI Bean that extends AdditionalWindowController

@@snip [applicationController.scala](../../../../../demos/windows/src/main/scala/com/sfxcode/sapphire/core/demo/windows/applicationController.scala) { #AdditionalWindowController }

@@snip [applicationController.scala](../../../../../demos/windows/src/main/scala/com/sfxcode/sapphire/core/demo/windows/applicationController.scala) { #SecondWindowController }


### Create a variable (in your ApplicationController for example)

@@snip [applicationController.scala](../../../../../demos/windows/src/main/scala/com/sfxcode/sapphire/core/demo/windows/applicationController.scala) { #SecondWindowControllerVar }

### Show second Window

Use x and y coordinates for window position.

@@snip [MainViewController.scala](../../../../../demos/windows/src/main/scala/com/sfxcode/sapphire/core/demo/windows/controller/MainViewController.scala) { #actionShowSecondWindow }

### Close second Window

@@snip [MainViewController.scala](../../../../../demos/windows/src/main/scala/com/sfxcode/sapphire/core/demo/windows/controller/MainViewController.scala) { #actionCloseSecondWindow }


## ModalWindowController

Modal Window is basically a special Instance of an AdditionalWindowController.
It can be used for custom dialogs, preferences panes and so on.
You only have to overwrite modality like in the sample code below:

@@snip [applicationController.scala](../../../../../demos/windows/src/main/scala/com/sfxcode/sapphire/core/demo/windows/applicationController.scala) { #ModalWindowController }

### Create a variable (in your ApplicationController for example)

@@snip [applicationController.scala](../../../../../demos/windows/src/main/scala/com/sfxcode/sapphire/core/demo/windows/applicationController.scala) { #ModalWindowControllerVar }


### Open Modal Window

@@snip [MainViewController.scala](../../../../../demos/windows/src/main/scala/com/sfxcode/sapphire/core/demo/windows/controller/MainViewController.scala) { #actionShowModalWindow }
