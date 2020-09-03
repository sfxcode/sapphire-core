# Changes

## Versions

### 2.0.0
* Removed FXValueFactory
* Remove CDI - see [migrations](MIGRATION.md)
* Sample Application with Deltaspike CDI in the issues demo
* FxmlLoader renamed to FxmlLocation

### 1.8.4
* Scala 2.12.12
* JavaFX 14.0.2.1

### 1.8.3
* Scala 2.13.3

### 1.8.0
* Showcase as core functions for better documentation
* Showcase Controller added
* Demos: ShowCase added (Demo & Documentation of sapphire-core)
* (Deprecation) FXValueFactory deprecated => use FXTableValueFactory
* (Feature) FXTreeTableValueFactory added
* (Feature) FXTableCellFactory added
* (Feature) ActionEvents:selectionFromActionEvent listView added
* (Fix) ViewVontroller updatePaneContent  didGainVisibilityFirstTime is now before didGainVisibility
* (Enhancement) ViewVontroller rename gainVisibility -> gainedVisibility

### 1.7.3
* BUGFIX - Expressions#registeredBean

### 1.7.2
* Expression Language now returns Options
* replace Jakarta with [Tomcat Expression Language](https://tomcat.apache.org/tomcat-8.0-doc/elapi/index.html)

### 1.7.1
* Move Expression Language from JUEL (EL 2) to [Jakarta Expression Language](https://github.com/eclipse-ee4j/el-ri)
* IssueTrackingLite Demo with scalafx support
* Expression Language Documentation

### 1.7.0
* Multiple WindowController Support
* DefaultWindowController, AdditionalWindowController
* Multiple WindowController Demo and Documentation

### 1.6.9
* JavaFX 13.0.2
* openwebbeans 2.0.12

### 1.6.7
* typesave config update
* openwebbeans 2.0.12

### 1.6.6
* FXMLLoading url and resources properties now Options
* FXMLLoading i18n support [sapphire-core/issues/18](https://github.com/sfxcode/sapphire-core/issues/18)
* ResourceBundleHolder added
* Add i18n method to Expression Language

### 1.6.5
* FXBeanAdapter refactor convert methods to KeyConverter
* Refactor FXBean by extending BeanProperties
* KeyConverter add meyhods for standard JavaFX Converters

### 1.6.4
* FXMLLoader suppport ContextClassLoader for Stream loading fallback
* use import scala.language.implicitConversions to mark implicits

### 1.6.3
* Fix Bug in FXMLoading

### 1.6.2
* CollectionExtensions: add implicits
* fix Map Changes Bug
* remove SceneExtensions

### 1.6.1
* ExtendedObservableList addChangeListener, addInvalidationListener
* Dependency Updates

### 1.6.0
* scalafx dependency removed
* SceneExtensions, CollectionExtensions added
* * Includes removed

### 1.5.1
* ConfigValues trait added
* dropped ConfigurationProvider
* Annotation for Fxml added: FxmlLoader

### 1.5.0
* JavaFX 12.0.1
* ScalaFX 12.0.1-R17
* re-enable scala 2.11
* Java 11, 12

### 1.4.5
* javafx 11.0.2
* org.apache.openwebbeans:openwebbeans-impl : 2.0.9 -> 2.0.10
* org.specs2:specs2-core:test               : 4.3.6 -> 4.4.0
* sbt 1.2.8


### 1.4.4

* com.typesafe.scala-logging:scala-logging  : 3.9.0 -> 3.9.2
* org.apache.openwebbeans:openwebbeans-impl : 2.0.8 -> 2.0.9
* org.json4s:json4s-native                  : 3.6.2 -> 3.6.3
* org.specs2:specs2-core                    : 4.3.5 -> 4.3.6

### 1.4.3
* FXBean Change management for complex case classes

### 1.4.2
* scala 2.12.8
* openwebbeans 2.0.8
* sbt 1.2.7

### 1.4.1
* sbt 1.2.6
* child controller handling

### 1.4.0
* OpenJFX 11
* Java 11
* drop scala 2.11 support
* scala 2.12.7
* scalafx 11-R16

### 1.3.4

* appController exit method


### 1.3.3

* FXBean Fixes
* FXBean property methods added e.g. getStringProperty


### 1.3.0

* Paradox Documentation
* Lifecycle refactored (canGainVisibility)
* ViewController children controller added


### 1.1.0

* ScalaFxmlLoading
* ScalaViewController

### 1.0.8

* integration tests
* deltaspike 1.5.2

### 1.0.7

* internationalization
* travis integration
* test cleanup
* Integration tests moved to it

