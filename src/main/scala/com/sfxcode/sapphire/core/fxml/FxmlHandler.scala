package com.sfxcode.sapphire.core.fxml

import java.io.{ IOException, InputStream }

import com.sfxcode.sapphire.core.application.ApplicationEnvironment
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.layout.Pane
import javafx.{ util => jfxu }

object FxmlHandler {

  var fxmlLoader: FXMLLoader = new FXMLLoader()

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
      inputStream = getResourceAsStream(path)
      fxmlLoader.setLocation(getClass.getResource(path))
      fxmlLoader.setResources(ApplicationEnvironment.defaultWindowController.resourceBundleForView(path))

      fxmlLoader.load(inputStream).asInstanceOf[Parent]

      val controller = fxmlLoader.getController[AnyRef]

      val rootPane = fxmlLoader.getRoot[javafx.scene.layout.Pane]
      (controller, rootPane)

    } catch {
      case e: Exception =>
        val message = String.format("can not load fxml from path [%s]", path)
        throw new IllegalStateException(message, e)
    } finally if (inputStream != null)
      try inputStream.close()
      catch {
        case e: IOException =>
      }
  }

  def getResourceAsStream(path: String): InputStream = {
    var result: InputStream = getClass.getResourceAsStream(path)

    if (result == null) {
      val rootPath = {
        if (path.startsWith("/"))
          path.substring(1)
        else
          path
      }
      result = Thread.currentThread.getContextClassLoader.getResourceAsStream(rootPath)
    }
    result
  }

}
