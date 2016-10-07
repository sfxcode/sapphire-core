package com.sfxcode.sapphire.core.demo.appdemo.controller

import javafx.event.ActionEvent

import com.sfxcode.sapphire.core.controller.ViewController

class Navigation2Controller extends ViewController {

  def mainWindowController: MainWindowController = {
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

  def actionToggleNavigation(event: ActionEvent) {
    mainWindowController.toggleNavigation()
  }

}
