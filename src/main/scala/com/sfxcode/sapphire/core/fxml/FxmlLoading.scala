package com.sfxcode.sapphire.core.fxml

import java.net.URL
import java.util.ResourceBundle

import com.sfxcode.sapphire.core.cdi.ApplicationEnvironment
import com.sfxcode.sapphire.core.cdi.provider.ConverterProvider
import com.sfxcode.sapphire.core.controller.ViewController
import com.sfxcode.sapphire.core.scene.NodeLocator
import com.sfxcode.sapphire.core.{ConfigValues, ResourceBundleHolder}
import javafx.scene.layout.Pane
import javax.inject.Inject

import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}

trait FxmlLoading extends NodeLocator with ConfigValues {
  private lazy val recourceBundleHolder = ResourceBundleHolder(
    resources.getOrElse(applicationEnvironment.resourceBundle))
  val mirror: ru.Mirror = ru.runtimeMirror(getClass.getClassLoader)
  @Inject
  var loader: FxmlHandler = _
  @Inject
  var applicationEnvironment: ApplicationEnvironment = _
  @Inject
  var converterFactory: ConverterProvider = _
  //var fxml: AnyRef = _
  var rootPane: Pane = _
  var location: Option[URL] = None
  var resources: Option[ResourceBundle] = None

  def i18n(key: String, params: Any*): String = {
    recourceBundleHolder.message(key, params: _*)
  }

  def getController[T <: ViewController](fxml: String = "")(implicit ct: ClassTag[T]): T = {
    val fxmlPath = guessFxmlPath(fxml, ct)

    loader.fxmlLoader.getNamespace.put("expression", applicationEnvironment.fxmlExpressionResolver)
    val loadResult = loader.loadFromDocument(fxmlPath.toString)
    val controller = loadResult._1.asInstanceOf[T]
    applicationEnvironment.registerController(controller)

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
