package com.sfxcode.sapphire.core.demo.tutorial.controller

import com.sfxcode.sapphire.core.controller.ViewController
import com.sfxcode.sapphire.core.demo.tutorial.ApplicationController
import com.sfxcode.sapphire.core.scene.ContentManager
import com.typesafe.scalalogging.LazyLogging

abstract class AbstractViewController extends ViewController with LazyLogging {

  override def didGainVisibility(): Unit =
    statusBarController.statusLabel.setText("%s loaded".format(getClass.getSimpleName))

  // load applicationController by Expression
  def applicationControllerByExpression: ApplicationController = {
    val maybeController = registeredBean[ApplicationController]
    var result = maybeController.get
    result
  }

  // load applicationController by CDI
  def applicationControllerByCDI: ApplicationController = {
    val result = getBean[ApplicationController]()
    result
  }

  // load applicationController by Application Environment
  def applicationControllerByApplicationEnvironment: ApplicationController = {
    val result = applicationEnvironment.defaultWindowController.asInstanceOf[ApplicationController]
    result
  }

  def mainViewController: MainViewController = applicationControllerByApplicationEnvironment.mainViewController

  def statusBarController: StatusBarController = getBean[StatusBarController]()

  def workspaceManager: ContentManager = mainViewController.workspaceManager

}
