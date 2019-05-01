package com.sfxcode.sapphire.core.demo.tutorial.controller

import javafx.event.ActionEvent
import javafx.scene.control.Button

import scalafx.Includes._

/**
 * Created by tom on 20.10.15.
 */
class NavigationController extends AbstractViewController {

  def actionClickButton(event: ActionEvent) {
    logger.debug(event.toString)
    val source = event.getSource

    // resolve controller by getViewController lookup
    val controller = getViewController[StatusBarController]()
    controller.foreach(c => c.updateLabel(event.getSource.asInstanceOf[Button]))
  }

  def actionToggleWorkspace(event: ActionEvent) {
    actionClickButton(event)
    if (workspaceManager.actualController == mainWindowController.workspaceController)
      workspaceManager.updatePaneContent(mainWindowController.secondWorkspaceController)
    else
      workspaceManager.updatePaneContent(mainWindowController.workspaceController)
  }

  def actionShowPersonController(event: ActionEvent): Unit = {
    actionClickButton(event)
    workspaceManager.updatePaneContent(mainWindowController.personController)
  }

}
