package com.sfxcode.sapphire.core.demo.test.controller

import com.sfxcode.sapphire.core.controller.ViewController
import javafx.scene.layout.Pane
import com.sfxcode.sapphire.core.scene.{ContentDidChangeEvent, ContentManager}
import javax.enterprise.event.Observes
import javafx.fxml.FXML

import com.typesafe.scalalogging.LazyLogging

class MainWindowController extends ViewController with LazyLogging   {

  lazy val workspaceController = getController[WorkspaceController]()
  lazy val workspace2Controller = getController[Workspace2Controller]()
  lazy val workspace3Controller = getController[Workspace3Controller]()
  lazy val defaultNavigationController = getController[DefaultNavigationController]()
  lazy val navigation2Controller = getController[Navigation2Controller]()

  @FXML
  var workspacePane: Pane = _
  @FXML
  var statusPane: Pane = _
  @FXML
  var navigationPane: Pane = _

  var workspaceManager:ContentManager = _
  var navigationManager:ContentManager = _

  override def didGainVisibilityFirstTime() {
    workspaceManager = ContentManager(workspacePane, this, workspaceController)
    navigationManager = ContentManager(navigationPane, this, navigation2Controller)
    navigationManager.updatePaneContent(defaultNavigationController)
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
    navigationManager.switchToLast()
  }

  def listenToChanges(@Observes event: ContentDidChangeEvent) {
    logger.debug(event.toString)
  }

}
