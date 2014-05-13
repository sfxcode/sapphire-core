package com.sfxcode.sapphire.core.controller

import javax.inject.Inject

import scala.reflect.ClassTag
import com.sfxcode.sapphire.core.cdi.FXMLHandler
import org.apache.deltaspike.core.api.provider.BeanProvider
import com.typesafe.config.ConfigFactory
import scalafx.scene.layout.Pane
import scala.reflect.runtime.{universe => ru}
import java.net.URL
import java.util.ResourceBundle

trait FxmlLoading {
  val m = ru.runtimeMirror(getClass.getClassLoader)

  @Inject
  var loader: FXMLHandler = _

  var fxml: Any = _
  var rootPane: Pane = _
  var location: URL = _
  var resources: ResourceBundle = _

  def getController[T <: ViewController](fxml: String = "")(implicit ct: ClassTag[T]): T = {
    var fxmlPath = guessFxmlPath(fxml, ct)
    val loadResult = loader.loadFromDocument(fxmlPath.toString)
    val controller = loadResult._1.asInstanceOf[T]
    ApplicationEnvironment.controllerMap.put(controller.getClass.getName, controller)

    controller.rootPane = loadResult._2
    controller
  }


  protected def guessFxmlPath[T <: ViewController](fxml: String, ct: ClassTag[T]): String = {
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
    fxmlPath
  }

  def getBean[T <: AnyRef](optional: Boolean = false)(implicit ct: ClassTag[T]): T = {
    val clazz = ct.runtimeClass
    BeanProvider.getContextualReference(clazz, optional).asInstanceOf[T]
  }

  def isLoadedFromFXML = fxml != null

}
