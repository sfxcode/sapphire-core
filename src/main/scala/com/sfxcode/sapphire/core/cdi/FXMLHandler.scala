package com.sfxcode.sapphire.core.cdi

import javax.inject.Inject
import javax.enterprise.context.ApplicationScoped
import scalafx.scene.layout.Pane
import com.sfxcode.sapphire.core.controller.{ApplicationEnvironment, ViewController}
import javafx.{fxml => jfxf}
import javafx.{util => jfxu}

import jfxf.FXMLLoader
import javafx.scene.Parent
import java.io.{IOException, InputStream}
import scalafxml.core.{NoDependencyResolver, ControllerDependencyResolver, FxmlProxyGenerator}
import com.sfxcode.sapphire.core.value.ReflectionTools

case class TestDependency(initialPath: String)

@ApplicationScoped
class FXMLHandler {
  @Inject
  var fxmlLoader: FXMLLoader = _

  def loadFromDocument(path: String, dependencyResolver: ControllerDependencyResolver = NoDependencyResolver): Parent = {
    var inputStream: InputStream = null
    val callback = new jfxu.Callback[Class[_], Object] {
      override def call(cls: Class[_]): Object = FxmlProxyGenerator(cls, dependencyResolver)
    }

    fxmlLoader.setLocation(null)
    fxmlLoader.setRoot(null)
    fxmlLoader.setController(null)
    fxmlLoader.setControllerFactory(callback)


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

  def getViewController(fxml: String, dependencyResolver: ControllerDependencyResolver = NoDependencyResolver): (AnyRef, Pane) = {
    loadFromDocument(fxml, dependencyResolver)
    val controller = fxmlLoader.getController[AnyRef]
   val rootPane = new Pane(fxmlLoader.getRoot[javafx.scene.layout.Pane])
//    val impl = ReflectionTools.getMemberValue(result, "impl").asInstanceOf[ViewController]
//    impl.rootPane = result.rootPane
//    impl.location = result.location
//    impl.resources = result.resources
    (controller, rootPane)
  }

}
