# FxmlLoading

## Features

- load fxml by different pattern (convention over configuration)
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

class MainViewController extends ViewController with LazyLogging {

  // workspaces
  lazy val workspaceController = getController[WorkspaceController]()
  // navigation
  lazy val defaultNavigationController = 
     getController[DefaultNavigationController]()
  
}

```

WorkspaceController fxml must be saved at ```/controller/Workspace.fxml```.


## Different FXML-Path lookup pattern

@@@ note { title=Hint }

In case of mixing this pattern, FXML-Path is resolved from Pattern 1 to 3
@@@

### Pattern 1: Define path in getController:

```scala
  lazy val controller = 
     getController[WorkspaceController]("/fxml/special/path/special_name.fxml")
```
@FxmlLoader(path="/fxml/widget/Person.fxml")
class PersonController extends AbstractViewController {
WorkspaceController fxml must be saved at ```/fxml/special/path/special_name.fxml```.

### Pattern 2: Define path in FxmlLoader Annotation:

```scala
@FxmlLoader(path="/fxml/widget/Person.fxml")
class PersonController extends AbstractViewController {
  // some stuff ...
}
```
WorkspaceController fxml must be saved at ```/fxml/widget/Person.fxml```.


### Pattern 3: Define path in application.conf:

```
sapphire.core.fxml.basePath="/fxml/" 
```

WorkspaceController fxml must be saved at ```/fxml/Workspace.fxml```.





