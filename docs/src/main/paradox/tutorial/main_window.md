# Tutorial Main Controller


## Main FXML File

The MainController in the /controller/MainController needs a valid FXML File.
The bindings for Navigation, Workspace and StatusBar Controller uses the **fx:id** values.

@@snip [Fxml](main_window.xml)

## MainController

For adding SubController and displaying their UI content following steps are needed:

1. Prepare SubController
2. Connect with FXML identifier
3. Define ContentManager
4. Load Controller in the MainController LifeCycle
5. Optional Prepare Functions for Controller switching
6. Optional CDI based Functions / Injection Points

### Prepare SubController

@@snip [MainController](../../../../../demos/tutorial/src/main/scala/com/sfxcode/sapphire/core/demo/tutorial/controller/MainController.scala) { #controllerLoading }

### Connect with FXML identifier

@@snip [MainController](../../../../../demos/tutorial/src/main/scala/com/sfxcode/sapphire/core/demo/tutorial//controller/MainController.scala) { #fxmlBinding }

### ContentManager

@@snip [MainController](../../../../../demos/tutorial/src/main/scala/com/sfxcode/sapphire/core/demo/tutorial//controller/MainController.scala) { #contentManager }

### Init Controller

@@snip [MainController](../../../../../demos/tutorial/src/main/scala/com/sfxcode/sapphire/core/demo/tutorial//controller/MainController.scala) { #didGainVisibilityFirstTime}


### CDI (optional)

Make use of CDI Observers, Injection Points, ...

@@snip [MainController](../../../../../demos/tutorial/src/main/scala/com/sfxcode/sapphire/core/demo/tutorial//controller/MainController.scala) { #cdi}