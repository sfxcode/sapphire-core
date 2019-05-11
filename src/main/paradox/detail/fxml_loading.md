# In Depth FxmlLoading

## Features

- load fxml by controller name (convention over configuration
- load fxml by custom name
- CDI powered ViewController (FXMLLoader, FXMLLoaderProvider)
- ViewController rootPane is bound to fxml root element

## Example

ViewController extends FxmlLoading trait.
FxmlLoading provide the
```scala
getController[T <: ViewController](fxml: String = "")
```
method. The package path of the controller is the default fxml directory.
[name]Controller is converted to [name].fxml (WorkspaceController -> Workspace.fxml).


```scala

package controller

class MainWindowController extends ViewController with LazyLogging {

  // workspaces
  lazy val workspaceController = getController[WorkspaceController]()
  // navigation
  lazy val defaultNavigationController = 
     getController[DefaultNavigationController]()
  
}

```

WorkspaceController fxml must be saved at ```/controller/Workspace.fxml```.


## Custom fxml path

### Option 1: Define path in getController:

```scala
  lazy val controller = 
     getController[WorkspaceController]("/fxml/special/path/special_name.fxml")
```
@FxmlLoader(path="/fxml/widget/Person.fxml")
class PersonController extends AbstractViewController {
WorkspaceController fxml must be saved at ```/fxml/special/path/special_name.fxml```.

### Option 2: Define path in FxmlLoader Annotation:

```scala
@FxmlLoader(path="/fxml/widget/Person.fxml")
class PersonController extends AbstractViewController {
  // some stuff ...
}
```
WorkspaceController fxml must be saved at ```/fxml/widget/Person.fxml```.


### Option 3: Define path in application.conf:

```
sapphire.core.fxml.basePath="/fxml/" 
```

WorkspaceController fxml must be saved at ```/fxml/Workspace.fxml```.





