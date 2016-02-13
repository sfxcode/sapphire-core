package com.sfxcode.sapphire.core.demo.appdemo.controller

import com.sfxcode.sapphire.core.controller.ViewController
import javafx.event.ActionEvent

class DefaultNavigationController extends ViewController {



  def mainWindowController:MainWindowController = {
    parent.asInstanceOf[MainWindowController]
  }

  def actionWorkspace1(event: ActionEvent) {
    mainWindowController.showWorkspace1()
  }

  def actionWorkspace2(event: ActionEvent) {
    mainWindowController.showWorkspace2()
  }

  def actionWorkspace3(event: ActionEvent) {
    mainWindowController.showWorkspace3()
  }

  def actionWorkspaceFromStack(event: ActionEvent) {
    mainWindowController.workspaceManager.loadFromStack()
  }

  def actionToggleNavigation(event: ActionEvent) {
    mainWindowController.toggleNavigation()
  }

}


