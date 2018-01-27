# Tutorial Main Window


## MainWindow FXML File

The MainWindowController in the ApplicationController needs a valid FXML File.
The bindings for Navigation, Workspace and StatusBar Controller uses the **fx:id** values.


@@snip [MainWindow](../../../it/resources/com/sfxcode/sapphire/core/demo/appdemo/controller/MainWindow.fxml)


## MainWindowController

For adding SubController and displaying their UI content following steps are needed:

1. Prepare SubController
2. Connect with FXML identifier
3. Define ContentManager
4. Load Controller in the MainWindowController LifeCycle
5. Optional Prepare Functions for Controller switching
6. Optional CDI based Functions / Injection Points

### Prepare SubController

@@snip [MainWindowController](../../../it/scala/com/sfxcode/sapphire/core/demo/appdemo/controller/MainWindowController.scala) { #controllerLoading }

### Connect with FXML identifier

@@snip [MainWindowController](../../../it/scala/com/sfxcode/sapphire/core/demo/appdemo/controller/MainWindowController.scala) { #fxmlBinding }

### ContentManager

@@snip [MainWindowController](../../../it/scala/com/sfxcode/sapphire/core/demo/appdemo/controller/MainWindowController.scala) { #contentManager }

### Init Controller

@@snip [MainWindowController](../../../it/scala/com/sfxcode/sapphire/core/demo/appdemo/controller/MainWindowController.scala) { #didGainVisibilityFirstTime}

### Switch Controller (optional)

Prepare helper functions for workspace view changes.

@@snip [MainWindowController](../../../it/scala/com/sfxcode/sapphire/core/demo/appdemo/controller/MainWindowController.scala) { #switchController}

### CDI (optional)

Make use of CDI Observers, Injection Points, ...

@@snip [MainWindowController](../../../it/scala/com/sfxcode/sapphire/core/demo/appdemo/controller/MainWindowController.scala) { #cdi}