package com.sfxcode.sapphire.core.cdi

import javax.inject.Inject
import javax.enterprise.context.ApplicationScoped
import scalafx.scene.layout.Pane
import javafx.{fxml => jfxf}
import javafx.{util => jfxu}

import jfxf.FXMLLoader
import javafx.scene.Parent
import java.io.{IOException, InputStream}

case class TestDependency(initialPath: String)

@ApplicationScoped
class FXMLHandler {
  @Inject
  var fxmlLoader: FXMLLoader = _
  var defaultCallback: Option[jfxu.Callback[Class[_], Object]] = None

  def loadFromDocument(path: String, callback: jfxu.Callback[Class[_], Object] = null): (AnyRef, Pane) = {
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

      val controller = fxmlLoader.getController[AnyRef]

      val rootPane = new Pane(fxmlLoader.getRoot[javafx.scene.layout.Pane])
      (controller, rootPane)

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



}
