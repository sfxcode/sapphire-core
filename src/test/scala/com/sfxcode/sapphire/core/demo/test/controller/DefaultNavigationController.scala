package com.sfxcode.sapphire.core.demo.test.controller

import com.sfxcode.sapphire.core.controller.ViewController
import scalafx.event.ActionEvent
import scalafxml.core.macros.sfxml

class DefaultNavigationController extends ViewController {

  def mainWindowController:MainWindowController = {
    parent.asInstanceOf[MainWindowController]
  }

}

@sfxml
class DefaultNavigationFxml(var viewController:DefaultNavigationController) {

  def mainWindowController = viewController.mainWindowController

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
