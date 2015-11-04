package com.sfxcode.sapphire.core.controller

import javax.enterprise.event.Observes

import com.sfxcode.sapphire.core.cdi.annotation.{FXStage, Startup}
import com.sfxcode.sapphire.core.fxml.FxmlLoading
import com.sfxcode.sapphire.core.scene.NodeLocator
import com.typesafe.scalalogging.LazyLogging

import scalafx.collections.ObservableMap
import scalafx.scene.{Parent, Scene}
import scalafx.stage.Stage

abstract class AppController extends FxmlLoading with NodeLocator with LazyLogging {


  val sceneMap = ObservableMap[Parent, Scene]()

  def startup(@Observes @FXStage @Startup stage: Stage) {
    applicationStartup(stage)
  }

  def applicationStartup(stage: Stage) {
    applicationEnvironment.stage = stage
    applicationEnvironment.scene = new Scene(stage.getScene)
    applicationEnvironment.applicationController = this
    applicationDidLaunch()
  }

  def applicationDidLaunch()

  def replaceSceneContent(newController: ViewController, resize: Boolean = true) {
    val oldController = applicationEnvironment.actualSceneController
    if (newController != null && oldController != newController) {
      if (oldController != null)
        try {
          oldController.willLooseVisibility()
        }
        catch {
          case e: Exception => logger.error(e.getMessage, e)
        }
      try {
        newController.willGainVisibility()
      }
      catch {
        case e: Exception => logger.error(e.getMessage, e)
      }
      replaceSceneContentWithNode(newController.rootPane, resize)
      applicationEnvironment.actualSceneController = newController
      if (oldController != null)
        try {
          oldController.didLooseVisibility()
        }
        catch {
          case e: Exception => logger.error(e.getMessage, e)
        }
    }
    if (!newController.gainVisibility) {
      try {
        newController.didGainVisibilityFirstTime()
      }
      catch {
        case e: Exception => logger.error(e.getMessage, e)
      }
      newController.gainVisibility = true
    }
    try {
      newController.didGainVisibility()
    }
    catch {
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
    applicationEnvironment.scene = newScene
    stage.sizeToScene()

  }

}
