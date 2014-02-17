# Sapphire

A JavaFX Application Framework for Scala User.

## Version

Actual Version is 0.8.0.

## Licence

Apache 2 License.

## Technology Stack

### Java / JDK 1.8

Sapphire runs agains the latest JDK 1.8 EAP version.

### JavaFX

Java UI Application Framework as replacement for Swing.

Sapphire depends on JavaFX 8.

### ScalaFX

A DSL for JavaFX written in Scala.

### Dependency Injection

Sapphire use Apache Deltaspike as CDI Abstraction Layer.

The default implementation depends on Apache OpenWebBeans.

### Expression Language

Expressions are resolved by JUEL.

## Features

### Application Environment powered by CDI

- Application Controller
- UI Controller loading
- Parameter Provider
- Configuration Provider
- FXML Loading

### Bean Enhancement

- Every Java / Scala Bean can be used for FXBean
- FXBean has additional support for java/scala Maps
- FXBean resolves Expressions on bean
- FXBean creates Properties needed for Binding on demand
- FXBean has change management by default

### Scala JavaFX Bean Binding

- Bindings by form id
- Binding with converter
- Adapter Pattern (FXBean Adapter)

### ViewController

- Controller Lifecycle
- Node Locator
- Controller CDI Additions

### FXML Support

- Simple load from controller
- Value Factory for using expressions inside fxml


## Demos

### Base

An example for simple controller exchange.

### Form

A simple Form Demo. It shows the usage of FXAdapter / Bindings.

### Login Demo

JavaFX Login Demo pimped by Sapphire.

### Issues Demo

JavaFX Issues Demo pimped by Sapphire.

