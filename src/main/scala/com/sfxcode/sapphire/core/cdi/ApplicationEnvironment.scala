package com.sfxcode.sapphire.core.cdi

import java.util.{Locale, ResourceBundle}

import com.sfxcode.sapphire.core.controller.{DefaultWindowController, ViewController}
import com.sfxcode.sapphire.core.el.Expressions
import com.sfxcode.sapphire.core.fxml.FxmlExpressionResolver
import com.sfxcode.sapphire.core.scene.NodePropertyResolver
import javafx.collections.{FXCollections, ObservableMap}
import javax.enterprise.context.ApplicationScoped
import javax.inject.Named

import scala.reflect.ClassTag

@Named
@ApplicationScoped
class ApplicationEnvironment extends Serializable {

  var controllerMap: ObservableMap[String, ViewController] = FXCollections.observableHashMap[String, ViewController]()

  var nodePropertyResolver = NodePropertyResolver()

  var fxmlExpressionResolver = new FxmlExpressionResolver[String, Any]

  var defaultWindowController: DefaultWindowController = _

  var resourceBundle: ResourceBundle = _

  def loadResourceBundle(path: String): Unit = {
    val classLoader = Thread.currentThread().getContextClassLoader
    resourceBundle = ResourceBundle.getBundle(path, Locale.getDefault(), classLoader)
  }

  def clearResourceBundleCache(): Unit = {
    val classLoader = Thread.currentThread().getContextClassLoader
    ResourceBundle.clearCache(classLoader)
  }

  def registerController(controller: ViewController): Unit = {
    controllerMap.put(controller.getClass.getName, controller)
    val simpleName: String = controller.getClass.getSimpleName
    val expressionName = "%s%s".format(simpleName.head.toLower, simpleName.tail)
    Expressions.register(expressionName, controller)
  }

  def getController[T <: ViewController](implicit ct: ClassTag[T]): Option[T] = {
    val key = ct.runtimeClass.getName
    if (controllerMap.containsKey(key)) {
      Some(controllerMap.get(key).asInstanceOf[T])
    } else {
      None
    }
  }

}

