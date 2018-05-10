package com.sfxcode.sapphire.core.demo.appdemo.controller

import javafx.fxml.FXML
import javafx.scene.layout.Pane
import javax.enterprise.event.Observes

import com.sfxcode.sapphire.core.controller.ViewController
import com.sfxcode.sapphire.core.scene.{ ContentDidChangeEvent, ContentManager }
import com.typesafe.scalalogging.LazyLogging

import scalafx.Includes._

class MainWindowController extends ViewController with LazyLogging {

  // #controllerLoading
  // workspaces
  lazy val workspaceController = getController[WorkspaceController]()
  lazy val secondWorkspaceController = getController[SecondWorkspaceController]()
  lazy val thirdWorkspaceController = getController[ThirdWorkspaceController]()

  // navigation
  lazy val defaultNavigationController = getController[DefaultNavigationController]()
  lazy val secondNavigationController = getController[SecondNavigationController]()

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
  // #contentManager

  // #didGainVisibilityFirstTime
  override def didGainVisibilityFirstTime() {
    workspaceManager = ContentManager(workspacePane, this, workspaceController)
    // enable stack based navigation for the workspaceManager
    workspaceManager.enableStack()
    navigationManager = ContentManager(navigationPane, this, defaultNavigationController)
  }
  // #didGainVisibilityFirstTime

  // #switchController

  def showWorkspaceController() {
    workspaceManager.updatePaneContent(workspaceController)
  }

  def showSecondWorkspaceController() {
    workspaceManager.updatePaneContent(secondWorkspaceController)
  }
  // #switchController

  def showThirdWorkspaceController() {
    workspaceManager.updatePaneContent(thirdWorkspaceController)
  }

  def toggleNavigation() {
    navigationManager.switchToLast()
    println(getViewController[DefaultNavigationController]())
    println(getViewController[MainWindowController]())
  }

  // #cdi
  def listenToChanges(@Observes event: ContentDidChangeEvent) {
    logger.debug(event.toString)
  }
  // #cdi
}
