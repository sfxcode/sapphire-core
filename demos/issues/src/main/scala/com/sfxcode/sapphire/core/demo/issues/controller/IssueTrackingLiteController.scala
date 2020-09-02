package com.sfxcode.sapphire.core.demo.issues.controller

import com.sfxcode.sapphire.core.controller.ViewController
import com.sfxcode.sapphire.core.demo.issues.EmptyName
import com.sfxcode.sapphire.core.demo.issues.model.{ Issue, IssueDataBase }
import com.sfxcode.sapphire.core.value._
import com.typesafe.scalalogging.LazyLogging
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.{ Button, ListView, TableView }
import javafx.scene.layout.AnchorPane
import scalafx.Includes._
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.SelectionMode

class IssueTrackingLiteController extends ViewController with LazyLogging {

  var emptyName: EmptyName = EmptyName("New Issue")

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

  lazy val issueAdapter = FXBeanAdapter[Issue](this, detailPane)

  val displayedProjectNames = new ObservableBuffer[String]()
  val displayedIssues = new ObservableBuffer[String]()

  override def didGainVisibilityFirstTime(): Unit = {
    super.didGainVisibilityFirstTime()
    detailPane.setVisible(false)
    deleteButton.setVisible(false)
    saveButton.setVisible(false)

    list.getSelectionModel.setSelectionMode(SelectionMode.Single)
    list.getSelectionModel.selectedItemProperty.onChange((_, oldValue, newValue) => updateProject(oldValue, newValue))
    table.getSelectionModel.selectedItemProperty.onChange((_, _, newValue) => selectIssue(newValue))

  }

  override def didGainVisibility() {
    super.didGainVisibility()
    issueAdapter.addBindings(KeyBindings("synopsis", "description"))
    issueAdapter.addBinding(saveButton.visibleProperty(), "_hasChanges")

    // issueAdapter.parent = detailPane
    displayedProjectNames.++=(IssueDataBase.projectNames.sortBy(name => name))
    list.setItems(displayedProjectNames)

    detailPane.visibleProperty.bind(issueAdapter.hasBeanProperty)
    deleteButton.visibleProperty.bind(issueAdapter.hasBeanProperty)
  }

  def selectedProjectName: Option[String] = {
    val selected = list.getSelectionModel.selectedItem
    if (selected.value == null)
      return None
    Some(selected.value)
  }

  def actionCreateIssue(event: ActionEvent) {
    selectedProjectName.foreach { projectName =>
      val newIssue = IssueDataBase.createIssue(projectName, emptyName.name)
      updateProject(projectName, projectName)
      selectIssue(newIssue)
    }
  }

  def actionDeleteIssue(event: ActionEvent) {
    selectedProjectName.foreach { projectName =>
      IssueDataBase.deleteIssue(issueAdapter.beanProperty.value.bean.id)
      updateProject(projectName, projectName)
    }
  }

  def actionSaveIssue(event: ActionEvent) {
    issueAdapter.clearChanges()
  }

  def selectIssue(issue: FXBean[Issue]) {
    issue match {
      case issue: FXBean[Issue] =>
        issueAdapter.revert()
        issueAdapter.beanProperty.value = issue
      case _ =>
        issueAdapter.unset()
    }
  }

  def updateProject(oldValue: String, newValue: String) {
    projectUnselected(oldValue)
    projectSelected(newValue)
  }

  def projectSelected(projectName: String) {
    projectName match {
      case name: String =>
        val newItems = IssueDataBase.projectsMap(projectName)
        newItems.foreach(item => table.getItems.add(item))
    }
  }

  def projectUnselected(projectName: String) {
    table.getItems.clear()
  }

}
