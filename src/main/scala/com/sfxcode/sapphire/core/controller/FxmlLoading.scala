package com.sfxcode.sapphire.core.controller

import java.net.URL
import java.util.ResourceBundle
import javax.inject.Inject

import com.sfxcode.sapphire.core.cdi.provider.ConverterProvider
import com.sfxcode.sapphire.core.cdi.{ApplicationEnvironment, FXMLHandler}
import com.typesafe.config.ConfigFactory

import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}
import scalafx.scene.Scene
import scalafx.scene.layout.Pane
import scalafx.stage.Stage

trait FxmlLoading extends NodeLocator {
  val m = ru.runtimeMirror(getClass.getClassLoader)

  @Inject
  var loader: FXMLHandler = _

  @Inject
  var applicationEnvironment:ApplicationEnvironment = _

  @Inject
  var converterFactory:ConverterProvider = _

  def stage: Stage = applicationEnvironment.stage

  def scene: Scene = applicationEnvironment.scene

  var fxml: AnyRef = _
  var rootPane: Pane = _
  var location: URL = _
  var resources: ResourceBundle = _

  def getController[T <: ViewController](fxml: String = "")(implicit ct: ClassTag[T]): T = {
    var fxmlPath = guessFxmlPath(fxml, ct)
    val loadResult = loader.loadFromDocument(fxmlPath.toString)
    val controller = loadResult._1.asInstanceOf[T]
    applicationEnvironment.controllerMap.put(controller.getClass.getName, controller)

    controller.rootPane = loadResult._2
    controller
  }


  protected def guessFxmlPath[T <: ViewController](path: String, ct: ClassTag[T]): String = {
    var result = path.toString
    if (path.isEmpty) {
      var basePath = ConfigFactory.load().getString("sapphire.core.fxml.basePath")
      if (basePath.isEmpty) {
        val guessed = ct.runtimeClass.getName.replace(".", "/").replace("Controller", "")
        result = "/%s.fxml".format(guessed)
      }
      else {
        val fxmlName = ct.runtimeClass.getSimpleName.replace("Controller", "")
        result = "%s%s.fxml".format(basePath, fxmlName)
      }
    }
    result
  }



  def isLoadedFromFXML = location != null

}
