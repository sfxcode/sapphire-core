package com.sfxcode.sapphire.core.demo.appdemo.controller

import javafx.event.ActionEvent

import com.sfxcode.sapphire.core.controller.ViewController
import com.sfxcode.sapphire.core.demo.appdemo.BaseApplicationController

class DefaultNavigationController extends ViewController {

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
    getBean[BaseApplicationController]().reload()
  }

  def actionToggleNavigation(event: ActionEvent) {
    mainWindowController.toggleNavigation()
  }

}

