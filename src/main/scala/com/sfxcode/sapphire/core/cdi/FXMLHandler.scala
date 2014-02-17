package com.sfxcode.sapphire.core.cdi

import javax.inject.{Inject, Named}
import javax.enterprise.context.ApplicationScoped
import scalafx.scene.layout.Pane
import com.sfxcode.sapphire.core.controller.{ApplicationEnvironment, ViewController}
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import java.io.{IOException, InputStream}

@ApplicationScoped
class FXMLHandler {
  @Inject
  var fxmlLoader: FXMLLoader = _

  def loadFromDocument(path: String): Parent = {
    var inputStream: InputStream = null

    fxmlLoader.setLocation(null)
    fxmlLoader.setRoot(null)
    fxmlLoader.setController(null)

    try {
      inputStream = getClass.getResourceAsStream(path)
      fxmlLoader.setLocation(getClass.getResource(path))
      fxmlLoader.load(inputStream).asInstanceOf[Parent]
    }
    catch {
      case e: Exception => {
        val message = String.format("can not load fxml from path [%s]", path)
        throw new IllegalStateException(message, e)
      }
    }
    finally {
      if (inputStream != null) {
        try {
          inputStream.close()
        }
        catch {
          case e: IOException => {
          }
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

}
