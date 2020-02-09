# Tutorial Main View Controller


## Main FXML File

The MainViewController in the /controller/MainViewController needs a valid FXML File.
The bindings for Navigation, Workspace and StatusBar Controller uses the **fx:id** values.

@@snip [Fxml](main_view.xml)

## MainViewController

For adding SubController and displaying their UI content following steps are needed:

1. Prepare SubController
2. Connect with FXML identifier
3. Define ContentManager
4. Load Controller in the MainViewController LifeCycle
5. Optional Prepare Functions for Controller switching
6. Optional CDI based Functions / Injection Points

### Prepare SubController

@@snip [MainViewController](../../../../../demos/tutorial/src/main/scala/com/sfxcode/sapphire/core/demo/tutorial/controller/MainViewController.scala) { #controllerLoading }

### Connect with FXML identifier

@@snip [MainViewController](../../../../../demos/tutorial/src/main/scala/com/sfxcode/sapphire/core/demo/tutorial//controller/MainViewController.scala) { #fxmlBinding }

### ContentManager

@@snip [MainViewController](../../../../../demos/tutorial/src/main/scala/com/sfxcode/sapphire/core/demo/tutorial//controller/MainViewController.scala) { #contentManager }

### Init Controller

@@snip [MainViewController](../../../../../demos/tutorial/src/main/scala/com/sfxcode/sapphire/core/demo/tutorial//controller/MainViewController.scala) { #didGainVisibilityFirstTime}


### CDI (optional)

Make use of CDI Observers, Injection Points, ...

@@snip [ApplicationObserver.scala](../../../../../demos/tutorial/src/main/scala/com/sfxcode/sapphire/core/demo/tutorial/ApplicationObserver.scala) { #cdi}