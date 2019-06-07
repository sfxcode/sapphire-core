package com.sfxcode.sapphire.core.controller

import java.util.ResourceBundle

import com.sfxcode.sapphire.core.fxml.FxmlLoading
import com.sfxcode.sapphire.core.scene.{ ContentDidChangeEvent, ContentWillChangeEvent, NodeLocator }
import com.typesafe.scalalogging.LazyLogging
import javax.enterprise.event.Event
import javax.inject.Inject
import scalafx.beans.property.ObjectProperty
import scalafx.collections.ObservableMap
import scalafx.scene.{ Parent, Scene }
import scalafx.stage.Stage

case class SceneControllerWillChangeEvent(windowController: WindowController, newController: ViewController, oldController: ViewController)

case class SceneControllerDidChangeEvent(windowController: WindowController, newController: ViewController, oldController: ViewController)

abstract class WindowController extends FxmlLoading with NodeLocator with LazyLogging {

  @Inject var sceneControllerWillChange: Event[SceneControllerWillChangeEvent] = _

  @Inject var sceneControllerChanged: Event[SceneControllerDidChangeEvent] = _

  var stageProperty = new ObjectProperty[Stage]()

  var sceneProperty = new ObjectProperty[Scene]()

  var sceneControllerProperty = new ObjectProperty[ViewController]()

  val sceneMap: ObservableMap[Parent, Scene] = ObservableMap[Parent, Scene]()

  def setStage(stage: Stage)

  def stage: Stage = stageProperty.value

  def scene: Scene = sceneProperty.value

  def isMainWindow:Boolean

  def actualSceneController: ViewController = sceneControllerProperty.value

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
