package com.sfxcode.sapphire.core.demo.tutorial.controller

import com.sfxcode.sapphire.core.controller.ViewController
import com.sfxcode.sapphire.core.scene.ContentManager
import com.typesafe.scalalogging.LazyLogging

abstract class AbstractViewController extends ViewController with LazyLogging {

  override def didGainVisibility(): Unit = {
    statusBarController.statusLabel.setText("%s loaded".format(getClass.getSimpleName))
  }

  def statusBarController: StatusBarController = getBean[StatusBarController]()

  def workspaceManager: ContentManager = mainWindowController.workspaceManager

  def mainWindowController: MainController = {
    parent.asInstanceOf[MainController]
  }

}
