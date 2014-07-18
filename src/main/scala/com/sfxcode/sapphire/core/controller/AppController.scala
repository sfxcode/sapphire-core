package com.sfxcode.sapphire.core.controller

import javax.enterprise.event.Observes
import com.sfxcode.sapphire.core.cdi.annotation.{Startup, FXStage}

import scalafx.stage.Stage
import scalafx.scene.{Node, Scene}
import scalafx.delegate.SFXDelegate
import com.typesafe.scalalogging.LazyLogging

abstract class AppController extends NodeLocator with FxmlLoading with LazyLogging {
  val sceneMap = new collection.mutable.HashMap[javafx.scene.Parent, javafx.scene.Scene]()

  def startup(@Observes @FXStage @Startup stage: Stage) {
    applicationStartup(stage)
  }

  def applicationStartup(stage: Stage) {
    ApplicationEnvironment.stage = stage
    ApplicationEnvironment.scene = new Scene(stage.getScene)
    ApplicationEnvironment.applicationController = this
    applicationDidLaunch()
  }


  def applicationDidLaunch()


  def replaceSceneContent(newController: ViewController, resize: Boolean = true) {
    val oldController = ApplicationEnvironment.actualSceneController
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
      ApplicationEnvironment.actualSceneController = newController
      if (oldController != null)
        try {
          oldController.didLooseVisibility()
        }
        catch {
          case e: Exception => logger.error(e.getMessage, e)
        }
    }
    if (!newController.firstTimeLoaded) {
      try {
        newController.didGainVisibilityFirstTime()
      }
      catch {
        case e: Exception => logger.error(e.getMessage, e)
      }
      newController.firstTimeLoaded = true
    }
    try {
      newController.didGainVisibility()
    }
    catch {
      case e: Exception => logger.error(e.getMessage, e)
    }
  }

  def replaceSceneContentWithNode(node: Node, resize: Boolean = true) {

    val fxNode = node match {
      case node: SFXDelegate[_] => node.delegate
      case _ => node
    }

    fxNode match {
      case parent: javafx.scene.Parent =>
        val newScene = sceneMap.getOrElse(parent, {
          val tempScene = new javafx.scene.Scene(parent)
          sceneMap.put(parent, tempScene)
          tempScene
        })

        stage.delegate.setScene(newScene)
        ApplicationEnvironment.scene = new Scene(stage.getScene)
        stage.sizeToScene()
      case _ =>
    }
  }

}
