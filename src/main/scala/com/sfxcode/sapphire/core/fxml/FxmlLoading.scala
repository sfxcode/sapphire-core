package com.sfxcode.sapphire.core.fxml

import java.net.URL
import java.util.ResourceBundle

import com.sfxcode.sapphire.core.application.ApplicationEnvironment
import com.sfxcode.sapphire.core.controller.ViewController
import com.sfxcode.sapphire.core.scene.NodeLocator
import com.sfxcode.sapphire.core.{ ConfigValues, ResourceBundleHolder }
import javafx.scene.layout.Pane

import scala.reflect.ClassTag
import scala.reflect.runtime.{ universe => ru }

trait FxmlLoading extends NodeLocator with ConfigValues {
  private lazy val recourceBundleHolder = ResourceBundleHolder(
    resources.getOrElse(ApplicationEnvironment.resourceBundle))
  val mirror: ru.Mirror = ru.runtimeMirror(getClass.getClassLoader)

  //var fxml: AnyRef = _
  var rootPane: Pane = _
  var location: Option[URL] = None
  var resources: Option[ResourceBundle] = None

  def i18n(key: String, params: Any*): String =
    recourceBundleHolder.message(key, params: _*)

  def getController[T <: ViewController](fxml: String = "")(implicit ct: ClassTag[T]): T = {
    val fxmlPath = guessFxmlPath(fxml, ct)

    FxmlHandler.fxmlLoader.getNamespace.put("expression", ApplicationEnvironment.fxmlExpressionResolver)
    val loadResult = FxmlHandler.loadFromDocument(fxmlPath.toString)
    val controller = loadResult._1.asInstanceOf[T]
    controller.rootPane = loadResult._2
    controller
  }

  protected def guessFxmlPath[T <: ViewController](path: String, ct: ClassTag[T]): String = {
    var result = path.toString

    // check annotatation value
    if (result.isEmpty)
      result = FxmlLoader.pathValue(ct)

    if (result.isEmpty) {
      // check configuration base path
      val basePath = configStringValue("sapphire.core.fxml.basePath")
      if (basePath.isEmpty) {
        // use runtime package name
        val guessed = ct.runtimeClass.getName.replace(".", "/").replace("Controller", "")
        result = "/%s.fxml".format(guessed)
      } else {
        val fxmlName = ct.runtimeClass.getSimpleName.replace("Controller", "")
        result = "%s%s.fxml".format(basePath, fxmlName)
      }
    }
    result
  }

  def isLoadedFromFXML: Boolean = location.isDefined

}
