package com.sfxcode.sapphire.core.demo.issues.controller

import com.sfxcode.sapphire.core.CollectionExtensions._
import com.sfxcode.sapphire.core.controller.ViewController
import com.sfxcode.sapphire.core.demo.issues.EmptyName
import com.sfxcode.sapphire.core.demo.issues.model.{Issue, IssueDataBase}
import com.sfxcode.sapphire.core.value._
import com.typesafe.scalalogging.LazyLogging
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.{Button, ListView, SelectionMode, TableView}
import javafx.scene.layout.AnchorPane
import javax.inject.Inject

class IssueTrackingLiteController extends ViewController with LazyLogging {

  lazy val issueAdapter = FXBeanAdapter[Issue](this, detailPane)
  val displayedProjectNames = FXCollections.observableArrayList[String]
  val displayedIssues = FXCollections.observableArrayList[String]
  @Inject
  var emptyName: EmptyName = _
  @FXML
  var table: TableView[FXBean[Issue]] = _
  @FXML
  var list: ListView[String] = _
  @FXML
  var saveButton: Button = _
  @FXML
  var deleteButton: Button = _
  @FXML
  var detailPane: AnchorPane = _

  override def didGainVisibilityFirstTime(): Unit = {
    super.didGainVisibilityFirstTime()
    detailPane.setVisible(false)
    deleteButton.setVisible(false)
    saveButton.setVisible(false)

    list.getSelectionModel.setSelectionMode(SelectionMode.SINGLE)
    list.getSelectionModel.selectedItemProperty
      .addListener((_, oldValue, newValue) => updateProject(oldValue, newValue))
    table.getSelectionModel.selectedItemProperty.addListener((_, _, newValue) => selectIssue(newValue))

  }

  override def didGainVisibility() {
    super.didGainVisibility()
    logger.debug(applicationEnvironment.viewControllerMap.toString())
    issueAdapter.addBindings(KeyBindings("synopsis", "description"))
    issueAdapter.addBinding(saveButton.visibleProperty(), "_hasChanges")

    // issueAdapter.parent = detailPane
    displayedProjectNames.addAll(IssueDataBase.projectNames.sorted())
    list.setItems(displayedProjectNames)

    detailPane.visibleProperty.bind(issueAdapter.hasBeanProperty)
    deleteButton.visibleProperty.bind(issueAdapter.hasBeanProperty)
  }

  def actionCreateIssue(event: ActionEvent) {
    selectedProjectName.foreach(projectName => {
      val newIssue = IssueDataBase.createIssue(projectName, emptyName.name)
      updateProject(projectName, projectName)
      selectIssue(newIssue)
    })
  }

  def selectedProjectName: Option[String] = {
    val selected = list.getSelectionModel.getSelectedItem
    if (selected == null)
      return None
    Some(selected)
  }

  def selectIssue(issue: FXBean[Issue]) {
    issue match {
      case issue: FXBean[Issue] =>
        issueAdapter.revert()
        issueAdapter.set(issue)
      case _ =>
        issueAdapter.unset
    }
  }

  def updateProject(oldValue: String, newValue: String) {
    projectUnselected(oldValue)
    projectSelected(newValue)
  }

  def projectSelected(projectName: String) {
    projectName match {
      case name: String =>
        val newItems = IssueDataBase.projectsMap.get(projectName)
        newItems.foreach(item => table.getItems.add(item))
    }
  }

  def projectUnselected(projectName: String) {
    table.getItems.clear()
  }

  def actionDeleteIssue(event: ActionEvent) {
    selectedProjectName.foreach(projectName => {
      IssueDataBase.deleteIssue(issueAdapter.beanProperty.getValue.bean.id)
      updateProject(projectName, projectName)
    })
  }

  def actionSaveIssue(event: ActionEvent) {
    issueAdapter.clearChanges()
  }

}
