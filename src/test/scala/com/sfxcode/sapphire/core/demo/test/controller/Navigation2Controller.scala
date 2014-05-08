package com.sfxcode.sapphire.core.demo.test.controller

import com.sfxcode.sapphire.core.controller.ViewController
import scalafxml.core.macros.sfxml
import scalafx.event.ActionEvent

class Navigation2Controller extends ViewController {

  def mainWindowController:MainWindowController = {
    parent.asInstanceOf[MainWindowController]
  }
}

@sfxml
class Navigation2Fxml(var viewController:Navigation2Controller)  {

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
