package com.sfxcode.sapphire.core.demo.tutorial.controller

import com.sfxcode.sapphire.core.controller.ViewController
import com.sfxcode.sapphire.core.scene.{ContentDidChangeEvent, ContentManager}
import com.typesafe.scalalogging.LazyLogging
import javafx.fxml.FXML
import javafx.scene.layout.Pane
import javax.enterprise.event.Observes

class MainController extends ViewController with LazyLogging {

  // #controllerLoading
  lazy val workspaceController: WorkspaceController =
    getController[WorkspaceController]()
  lazy val barChartController: BarChartController =
    getController[BarChartController]()
  lazy val personController: PersonController =
    getController[PersonController]()
  lazy val navigationController: NavigationController =
    getController[NavigationController]()
  lazy val statusBarController: StatusBarController =
    getBean[StatusBarController]()
  // #controllerLoading

  // #fxmlBinding
  @FXML
  var workspacePane: Pane = _
  @FXML
  var statusPane: Pane = _
  @FXML
  var navigationPane: Pane = _
  // #fxmlBinding

  // #contentManager
  var workspaceManager: ContentManager = _
  var navigationManager: ContentManager = _
  var statusBarManager: ContentManager = _
  // #contentManager

  // #didGainVisibilityFirstTime
  override def didGainVisibilityFirstTime() {
    navigationManager = ContentManager(navigationPane, this, navigationController)
    statusBarManager = ContentManager(statusPane, this, statusBarController)
    workspaceManager = ContentManager(workspacePane, this, workspaceController)
  }
  // #didGainVisibilityFirstTime

  // #cdi
  def listenToChanges(@Observes event: ContentDidChangeEvent) {
    logger.debug(event.toString)
  }
  // #cdi
}
