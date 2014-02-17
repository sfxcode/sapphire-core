package com.sfxcode.sapphire.core.demo.base.controller

import com.sfxcode.sapphire.core.controller.ViewController
import javafx.scene.layout.Pane
import javafx.fxml.FXML
import javafx.event.ActionEvent
import com.sfxcode.sapphire.core.scene.{ContentDidChangeEvent, ContentManager}
import javax.enterprise.event.Observes
import com.sfxcode.sapphire.core.event.ActionEvents


class MainWindowController extends ViewController with ActionEvents {

  lazy val workspaceController = getController[WorkspaceController]()
  lazy val workspace2Controller = getController[Workspace2Controller]()
  lazy val workspace3Controller = getController[Workspace3Controller]()
  lazy val defaultNavigationController = getController[DefaultNavigationController]()
  lazy val navigation2Controller = getController[Navigation2Controller]()

  //lazy val testController = new TestController()
  //lazy val statusController = new StatusBarController()

  lazy val workspaceManager = ContentManager(workspacePane, this, null)
  lazy val navigationManager = ContentManager(navigationPane, this, null)
  //lazy val statusBarManager = ContentManager(statusBar, this, statusController)

  @FXML
  var workspacePane: Pane = _

  @FXML
  var statusPane: Pane = _

  @FXML
  var navigationPane: Pane = _

  override def didGainVisibility() {
    super.didGainVisibility()
    navigationManager.updatePaneContent(navigation2Controller)
    navigationManager.updatePaneContent(defaultNavigationController)
    showWorkspace1()
  }

  def showWorkspace1() {
    workspaceManager.updatePaneContent(workspaceController)
  }

  def showWorkspace2() {
    workspaceManager.updatePaneContent(workspace2Controller)
  }

  def showWorkspace3() {
    workspaceManager.updatePaneContent(workspace3Controller)
  }

  def toggleNavigation() {
    navigationManager.switchToLast
  }

  def listenToChanges(@Observes event: ContentDidChangeEvent) {
    System.out.println(event)
  }

}
