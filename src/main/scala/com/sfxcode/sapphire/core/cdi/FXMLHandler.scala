package com.sfxcode.sapphire.core.cdi

import javax.inject.Inject
import javax.enterprise.context.ApplicationScoped
import scalafx.scene.layout.Pane
import com.sfxcode.sapphire.core.controller.{ViewController, ApplicationEnvironment}
import javafx.{fxml => jfxf}
import javafx.{util => jfxu}

import jfxf.FXMLLoader
import javafx.scene.Parent
import java.io.{IOException, InputStream}
import scalafxml.core.{NoDependencyResolver, ControllerDependencyResolver, FxmlProxyGenerator}

case class TestDependency(initialPath: String)

@ApplicationScoped
class FXMLHandler {
  @Inject
  var fxmlLoader: FXMLLoader = _
  var defaultCallback: Option[jfxu.Callback[Class[_], Object]] = None

  def loadFromDocument(path: String, callback: jfxu.Callback[Class[_], Object] = null): Parent = {
    // save cdi callback
    if (!defaultCallback.isDefined)
      defaultCallback = Some(fxmlLoader.getControllerFactory)

    var inputStream: InputStream = null
    fxmlLoader.setLocation(null)
    fxmlLoader.setRoot(null)
    fxmlLoader.setController(null)
    if (callback != null)
      fxmlLoader.setControllerFactory(callback)
    else
      fxmlLoader.setControllerFactory(defaultCallback.get)

    try {
      inputStream = getClass.getResourceAsStream(path)
      fxmlLoader.setLocation(getClass.getResource(path))
      fxmlLoader.load(inputStream).asInstanceOf[Parent]

    }
    catch {
      case e: Exception =>
        val message = String.format("can not load fxml from path [%s]", path)
        throw new IllegalStateException(message, e)
    }
    finally {
      if (inputStream != null) {
        try {
          inputStream.close()
        }
        catch {
          case e: IOException =>
        }
      }
    }
  }

  def getViewController[T <: ViewController](fxml: String): T = {
    loadFromDocument(fxml)
    val result = fxmlLoader.getController[T]
    ApplicationEnvironment.controllerMap.put(result.getClass.getName, result)
    result.rootPane = new Pane(fxmlLoader.getRoot[javafx.scene.layout.Pane])
    result
  }

  def getViewController(fxml: String, dependencyResolver: ControllerDependencyResolver = NoDependencyResolver): (AnyRef, Pane) = {

    val callback = new jfxu.Callback[Class[_], Object] {
      override def call(cls: Class[_]): Object = FxmlProxyGenerator(cls, dependencyResolver)
    }

    loadFromDocument(fxml, callback)
    val controller = fxmlLoader.getController[AnyRef]
    val rootPane = new Pane(fxmlLoader.getRoot[javafx.scene.layout.Pane])
    (controller, rootPane)
  }

}
