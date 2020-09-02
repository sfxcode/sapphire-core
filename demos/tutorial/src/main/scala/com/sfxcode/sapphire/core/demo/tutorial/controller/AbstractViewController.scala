package com.sfxcode.sapphire.core.demo.tutorial.controller

import com.sfxcode.sapphire.core.application.{ ApplicationEnvironment, FXApp }
import com.sfxcode.sapphire.core.controller.ViewController
import com.sfxcode.sapphire.core.demo.tutorial.ApplicationController
import com.sfxcode.sapphire.core.scene.ContentManager
import com.typesafe.scalalogging.LazyLogging

abstract class AbstractViewController extends ViewController with LazyLogging {

  override def didGainVisibility(): Unit =
    statusBarController.statusLabel.setText("%s loaded".format(getClass.getSimpleName))

  // load applicationController
  def applicationController: ApplicationController =
    FXApp.App.applicationController.asInstanceOf[ApplicationController]

  // load applicationController by Expression
  def applicationControllerByExpression: ApplicationController = {
    val maybeController = registeredBean[ApplicationController]
    var result = maybeController.get
    result
  }

  // load applicationController by Application Environment
  def applicationControllerByApplicationEnvironment: ApplicationController = {
    val result = ApplicationEnvironment.defaultWindowController.asInstanceOf[ApplicationController]
    result
  }

  def mainViewController: MainViewController = applicationControllerByApplicationEnvironment.mainViewController

  def statusBarController: StatusBarController = mainViewController.statusBarController

  def workspaceManager: ContentManager = mainViewController.workspaceManager

}
