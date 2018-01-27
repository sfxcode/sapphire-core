package com.sfxcode.sapphire.core.demo.appdemo.controller

import javafx.event.ActionEvent

import com.sfxcode.sapphire.core.controller.ViewController

class SecondNavigationController extends ViewController {

  def mainWindowController: MainWindowController = {
    parent.asInstanceOf[MainWindowController]
  }

  def actionWorkspace1(event: ActionEvent) {
    mainWindowController.showWorkspaceController()
  }

  def actionWorkspace2(event: ActionEvent) {
    mainWindowController.showSecondWorkspaceController()
  }

  def actionWorkspace3(event: ActionEvent) {
    mainWindowController.showThirdWorkspaceController()
  }

  def actionToggleNavigation(event: ActionEvent) {
    mainWindowController.toggleNavigation()
  }

}
