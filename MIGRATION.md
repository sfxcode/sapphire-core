# Migrations

## 1.6.x to 2.0.0
* CDI dependencies are removed
* ApplicationScoped Beans are now Singletons
* Best way to access applicationController is now: ApplicationEnvironment.applicationController[ApplicationController]
* Use CDI if needed with MacWire, Guice, or other CDI Framework (CDI example in issues demo)
* Application must extend now BaseApplication and needs to implement applicationController as val
* FxmlLoader must be renamed to FxmlLocation

```scala
object Application extends BaseApplication {

  // some custom code ...
  override val applicationController: BaseApplicationController = new ApplicationController
}
```

* ApplicationController must extend now BaseApplicationController

```scala
class ApplicationController extends BaseApplicationController with LazyLogging {

  // some custom code ...

}
```

## 1.6.5 to 1.6.6
* FXMLLoading url and resources properties now Options

## 1.6.1 to 1.6.2
* CollectionExtensions and ConfigValues moved to package root
* Remove SceneExtensions

## 1.x to 1.6
* scalafx dependency removed (add dependency on project if needed)
* Includes Removed => use BeanConversions trait
* Prefer WindowController instead of deprecated AppController
