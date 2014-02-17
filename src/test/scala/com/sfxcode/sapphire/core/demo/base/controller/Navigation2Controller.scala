package com.sfxcode.sapphire.core.demo.base.controller

import com.sfxcode.sapphire.core.controller.ViewController
import javafx.event.ActionEvent

class Navigation2Controller extends ViewController {
  def actionWorkspace1(event: ActionEvent) {
    mainWindowController.showWorkspace1()
  }

  def actionWorkspace2(event: ActionEvent) {
    mainWindowController.showWorkspace2()
  }

  def actionWorkspace3(event: ActionEvent) {
    mainWindowController.showWorkspace3()
  }

  def actionToggleNavigation(event: ActionEvent) {
    mainWindowController.toggleNavigation()
  }

  def mainWindowController:MainWindowController = {
    parent.asInstanceOf[MainWindowController]
  }
}
