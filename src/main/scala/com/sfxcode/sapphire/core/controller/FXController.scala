package com.sfxcode.sapphire.core.controller

import javax.inject.Inject

import scala.reflect.ClassTag
import com.sfxcode.sapphire.core.cdi.FXMLHandler
import org.apache.deltaspike.core.api.provider.BeanProvider
import com.typesafe.config.ConfigFactory
import scalafxml.core.{NoDependencyResolver, ControllerDependencyResolver}
import scalafx.scene.layout.Pane
import scala.reflect.runtime.{universe => ru}
import com.sfxcode.sapphire.core.value.ReflectionTools
import java.net.URL
import java.util.ResourceBundle

trait FXController {
  val m = ru.runtimeMirror(getClass.getClassLoader)

  @Inject
  var loader: FXMLHandler = _

  var fxml: Any = _
  var rootPane: Pane = _
  var location: URL = _
  var resources: ResourceBundle = _

  def getController[T <: ViewController](fxml: String = "", dependencyResolver: ControllerDependencyResolver = NoDependencyResolver)(implicit ct: ClassTag[T]): T = {

    var result: ViewController = null
    var fxmlPath = fxml.toString
    if (fxml.isEmpty) {
      var basePath = ConfigFactory.load().getString("sapphire.core.fxml.basePath")
      if (basePath.isEmpty) {
        val guessed = ct.runtimeClass.getName.replace(".", "/").replace("Controller", "")
        fxmlPath = "/%s.fxml".format(guessed)
      }
      else {
        val fxmlName = ct.runtimeClass.getSimpleName.replace("Controller", "")
        fxmlPath = "%S%s.fxml".format(basePath, fxmlName)
      }
    }

    // use controller class if it implements  Initializable
    if (hasInitializableInterface(ct.runtimeClass))
      result = getControllerFromFxml[T](fxmlPath)

    // use sfxml annotated class otherwise
    if (result == null)
      result = getControllerFromScalaFxml[T](fxmlPath, dependencyResolver)

    result.asInstanceOf[T]

  }

  private def hasInitializableInterface(c: Class[_]): Boolean = {
    val interfaces = c.getInterfaces
    var result = false
    interfaces.foreach(c => {
      if ("javafx.fxml.Initializable".equals(c.getName))
        result = true
    })
    if (!result && c.getSuperclass != null)
      return hasInitializableInterface(c.getSuperclass)

    result
  }

  private def getControllerFromFxml[T <: ViewController](fxmlPath: String): T = {
    loader.getViewController[T](fxmlPath.toString)
  }

  private def getControllerFromScalaFxml[T <: ViewController](fxmlPath: String, dependencyResolver: ControllerDependencyResolver)(implicit ct: ClassTag[T]): T = {
    val fxmlResult = loader.getViewController(fxmlPath, dependencyResolver)

    val result = getBean[T]()
    result.fxml = fxmlResult._1
    result.rootPane = fxmlResult._2

    val impl = ReflectionTools.getMemberValue(result.fxml, "impl")
    if (ReflectionTools.getFieldType(impl, "viewController").isDefined)
      ReflectionTools.setMemberValue(impl, "viewController", result)

    ApplicationEnvironment.controllerMap.put(result.getClass.getName, result)
    result
  }


  def getBean[T <: AnyRef](optional: Boolean = false)(implicit ct: ClassTag[T]): T = {
    val clazz = ct.runtimeClass
    BeanProvider.getContextualReference(clazz, optional).asInstanceOf[T]
  }

  def isLoadedFromFXML = fxml != null

}
