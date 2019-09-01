package com.sfxcode.sapphire.core.controller

import java.util.ResourceBundle

import com.sfxcode.sapphire.core.CollectionExtensions._
import com.sfxcode.sapphire.core.fxml.FxmlLoading
import com.sfxcode.sapphire.core.scene.NodeLocator
import com.typesafe.scalalogging.LazyLogging
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.ObservableMap
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage
import javax.enterprise.event.Event
import javax.inject.Inject
case class SceneControllerWillChangeEvent(windowController: WindowController, newController: ViewController, oldController: ViewController)

case class SceneControllerDidChangeEvent(windowController: WindowController, newController: ViewController, oldController: ViewController)

abstract class WindowController extends FxmlLoading with NodeLocator with LazyLogging {

  @Inject var sceneControllerWillChange: Event[SceneControllerWillChangeEvent] = _

  @Inject var sceneControllerChanged: Event[SceneControllerDidChangeEvent] = _

  var stageProperty = new SimpleObjectProperty[Stage]()

  var sceneProperty = new SimpleObjectProperty[Scene]()

  var sceneControllerProperty = new SimpleObjectProperty[ViewController]()

  val sceneMap: ObservableMap[Parent, Scene] = Map[Parent, Scene]()

  def setStage(stage: Stage): Unit = {
    stageProperty.set(stage)
    sceneProperty.set(stage.getScene)
  }

  def stage: Stage = stageProperty.getValue

  def scene: Scene = sceneProperty.getValue

  def isMainWindow: Boolean

  def actualSceneController: ViewController = sceneControllerProperty.getValue

  def replaceSceneContent(newController: ViewController, resize: Boolean = true) {
    val oldController = actualSceneController
    if (newController != null && newController != oldController && newController.canGainVisibility
      && (oldController == null || oldController.shouldLooseVisibility)) {
      if (oldController != null)
        try {
          oldController.willLooseVisibility()
        } catch {
          case e: Exception => logger.error(e.getMessage, e)
        }
      try {
        newController.windowController.set(this)
        newController.willGainVisibility()
      } catch {
        case e: Exception => logger.error(e.getMessage, e)
      }
      sceneControllerWillChange.fire(SceneControllerWillChangeEvent(this, newController, oldController))

      replaceSceneContentWithNode(newController.rootPane, resize)
      sceneControllerProperty.set(newController)
      if (oldController != null)
        try {
          oldController.didLooseVisibility()
        } catch {
          case e: Exception => logger.error(e.getMessage, e)
        }
    }
    if (!newController.gainVisibility) {
      try {
        newController.didGainVisibilityFirstTime()
      } catch {
        case e: Exception => logger.error(e.getMessage, e)
      }
      newController.gainVisibility = true
    }
    try {
      newController.didGainVisibility()
      sceneControllerChanged.fire(SceneControllerDidChangeEvent(this, newController, oldController))

    } catch {
      case e: Exception => logger.error(e.getMessage, e)
    }
  }

  def replaceSceneContentWithNode(content: Parent, resize: Boolean = true) {

    val newScene = sceneMap.getOrElse(content, {
      val scene = new Scene(content)
      sceneMap.put(content, scene)
      scene
    })

    stage.setScene(newScene)
    sceneProperty.set(newScene)
    stage.sizeToScene()

  }

  def resourceBundleForView(viewPath: String): ResourceBundle = applicationEnvironment.resourceBundle

}
