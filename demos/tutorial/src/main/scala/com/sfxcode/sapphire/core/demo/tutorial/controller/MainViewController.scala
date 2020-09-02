package com.sfxcode.sapphire.core.demo.tutorial.controller

import com.sfxcode.sapphire.core.controller.ViewController
import com.sfxcode.sapphire.core.scene.ContentManager
import com.typesafe.scalalogging.LazyLogging
import javafx.fxml.FXML
import javafx.scene.layout.Pane

class MainViewController extends ViewController with LazyLogging {

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
    new StatusBarController()
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
    super.didGainVisibility()
    navigationManager = ContentManager(navigationPane, this, navigationController)
    statusBarManager = ContentManager(statusPane, this, statusBarController)
    workspaceManager = ContentManager(workspacePane, this, workspaceController)
  }
  // #didGainVisibilityFirstTime

}
