package com.sfxcode.sapphire.core.demo.test.controller

import com.sfxcode.sapphire.core.controller.ViewController
import scalafx.scene.layout.Pane
import com.sfxcode.sapphire.core.scene.{ContentDidChangeEvent, ContentManager}
import javax.enterprise.event.Observes
import scalafxml.core.macros.sfxml
import javax.inject.Named
import javax.enterprise.context.ApplicationScoped

@Named
@ApplicationScoped
class MainWindowController extends ViewController  {
  def ui = fxml.asInstanceOf[MainWindowFxml]

  lazy val workspaceController = getController[WorkspaceController]()
  lazy val workspace2Controller = getController[Workspace2Controller]()
  lazy val workspace3Controller = getController[Workspace3Controller]()
  lazy val defaultNavigationController = getController[DefaultNavigationController]()
  lazy val navigation2Controller = getController[Navigation2Controller]()


  var workspaceManager:ContentManager = _
  var navigationManager:ContentManager = _

  override def didGainVisibilityFirstTime() {
    navigationManager = ContentManager(ui.navigationPane, this, navigation2Controller)
    navigationManager.updatePaneContent(defaultNavigationController)
    workspaceManager = ContentManager(ui.workspacePane, this, workspaceController)
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
    System.out.println(event)
  }

}

@sfxml
class MainWindowFxml(val workspacePane: Pane, val statusPane: Pane, val navigationPane: Pane)
