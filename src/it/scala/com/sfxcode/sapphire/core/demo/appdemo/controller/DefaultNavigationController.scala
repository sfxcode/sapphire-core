package com.sfxcode.sapphire.core.demo.appdemo.controller

import com.sfxcode.sapphire.core.controller.ViewController
import com.sfxcode.sapphire.core.demo.appdemo.ApplicationController
import javafx.event.ActionEvent

class DefaultNavigationController extends ViewController {

  def applicationController:ApplicationController =   applicationEnvironment.applicationController.asInstanceOf[ApplicationController]


  def mainWindowController: MainWindowController = {
    parent.asInstanceOf[MainWindowController]
  }

  def actionShowWorkspaceController(event: ActionEvent) {
    mainWindowController.showWorkspaceController()
  }

  def actionShowSecondWorkspaceController(event: ActionEvent) {
    mainWindowController.showSecondWorkspaceController()
  }

  def actionShowThirdWorkspaceController(event: ActionEvent) {
    mainWindowController.showThirdWorkspaceController()
  }

  def actionLoadWorkspaceFromStack(event: ActionEvent) {
    mainWindowController.workspaceManager.loadFromStack()
  }

  def actionReload(event: ActionEvent) {
    getBean[ApplicationController]().reload()
  }

  def actionToggleNavigation(event: ActionEvent) {
    mainWindowController.toggleNavigation()
  }

  def actionExit(event: ActionEvent): Unit = {
    applicationController.exit()
  }

}

