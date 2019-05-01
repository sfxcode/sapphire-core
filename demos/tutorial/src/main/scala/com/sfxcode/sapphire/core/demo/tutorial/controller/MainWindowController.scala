package com.sfxcode.sapphire.core.demo.tutorial.controller

import javafx.fxml.FXML
import javafx.scene.layout.Pane
import javax.enterprise.event.Observes

import com.sfxcode.sapphire.core.controller.ViewController
import com.sfxcode.sapphire.core.scene.{ ContentDidChangeEvent, ContentManager }
import com.typesafe.scalalogging.LazyLogging

import scalafx.Includes._

class MainWindowController extends ViewController with LazyLogging {

  lazy val workspaceController = getController[WorkspaceController]()
  lazy val secondWorkspaceController = getController[SecondWorkspaceController]()
  lazy val personController = getController[PersonController]()
  lazy val navigationController = getController[NavigationController]()
  lazy val statusBarController = getBean[StatusBarController]()

  @FXML
  var workspacePane: Pane = _
  @FXML
  var statusPane: Pane = _
  @FXML
  var navigationPane: Pane = _

  var workspaceManager: ContentManager = _
  var navigationManager: ContentManager = _
  var statusBarManager: ContentManager = _

  override def didGainVisibilityFirstTime() {
    navigationManager = ContentManager(navigationPane, this, navigationController)
    statusBarManager = ContentManager(statusPane, this, statusBarController)
    workspaceManager = ContentManager(workspacePane, this, workspaceController)
  }

  def listenToChanges(@Observes event: ContentDidChangeEvent) {
    logger.debug(event.toString)
  }

}
