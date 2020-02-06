package com.sfxcode.sapphire.core.demo.tutorial.controller

import com.sfxcode.sapphire.core.demo.tutorial.ApplicationController
import javafx.event.ActionEvent
import javafx.scene.control.Button

class NavigationController extends AbstractViewController {

  def actionToggleWorkspace(event: ActionEvent) {
    actionClickButton(event)
    val actualController    = workspaceManager.actualController
    val barChartController  = mainViewController.barChartController
    val workspaceController = mainViewController.workspaceController
    if (actualController == workspaceController) {
      workspaceManager.updatePaneContent(barChartController)
    }
    else
      workspaceManager.updatePaneContent(workspaceController)
  }

  def actionShowPersonController(event: ActionEvent): Unit = {
    actionClickButton(event)
    val personController = mainViewController.personController
    workspaceManager.updatePaneContent(personController)
  }

  def actionClickButton(event: ActionEvent) {
    logger.debug(event.toString)

    // resolve controller by getViewController lookup
    val controller = getViewController[StatusBarController]()
    controller.foreach { c =>
      val button = event.getSource.asInstanceOf[Button]
      c.updateLabel(button)
    }
  }

  def actionReload(event: ActionEvent): Unit = {
    actionClickButton(event)
    getBean[ApplicationController]().reload()
  }

}
