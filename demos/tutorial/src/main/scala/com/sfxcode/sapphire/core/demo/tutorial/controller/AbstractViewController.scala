
package com.sfxcode.sapphire.core.demo.tutorial.controller

import com.sfxcode.sapphire.core.controller.ViewController
import com.typesafe.scalalogging.LazyLogging

abstract class AbstractViewController extends ViewController with LazyLogging {

  override def didGainVisibility(): Unit = {
    statusBarController.statusLabel.text = "%s loaded".format(getClass.getSimpleName)
  }

  def mainWindowController: MainWindowController = {
    parent.asInstanceOf[MainWindowController]
  }

  /**
   *
   * @return
   */
  def statusBarController = getBean[StatusBarController]()

  /**
   *
   * @return workspace manager resolved by parent
   */
  def workspaceManager = mainWindowController.workspaceManager

}
