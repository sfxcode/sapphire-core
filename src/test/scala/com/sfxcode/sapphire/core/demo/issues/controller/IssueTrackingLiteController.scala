package com.sfxcode.sapphire.core.demo.issues.controller

import com.sfxcode.sapphire.core.controller.ViewController
import javafx.event.ActionEvent
import com.sfxcode.sapphire.core.demo.issues.model.{Issue, IssueDataBase}
import javafx.fxml.FXML
import javafx.scene.control._
import scalafx.collections._
import scalafx.Includes._
import scalafx.scene.control.SelectionMode
import com.sfxcode.sapphire.core.value.{FXBean, FXBeanAdapter, KeyBindings}
import javafx.scene.layout.AnchorPane


class IssueTrackingLiteController extends ViewController  {
  @FXML
  var list: ListView[String] = _

  @FXML
  var table: TableView[FXBean[Issue]] = _

  @FXML
  var detailPane: AnchorPane = _

  @FXML
  var deleteButton: Button = _

  @FXML
  var saveButton: Button = _

  lazy val issueAdapter = FXBeanAdapter[Issue](this, detailPane)

  val displayedProjectNames = new ObservableBuffer[String]()
  val displayedIssues = new ObservableBuffer[String]()


  override def didGainVisibility() {
    super.didGainVisibility()
    issueAdapter.addBindings(KeyBindings("synopsis", "description"))
    issueAdapter.addBinding(saveButton.visibleProperty(), "_hasChanges")

    detailPane.setVisible(false)
    deleteButton.setVisible(false)
    saveButton.setVisible(false)

    // issueAdapter.parent = detailPane
    displayedProjectNames.++=(IssueDataBase.projectNames.sortBy(name => name))
    list.setItems(displayedProjectNames)
    list.getSelectionModel.setSelectionMode(SelectionMode.SINGLE)
    list.getSelectionModel.selectedItemProperty.onChange((_, oldValue, newValue) => updateProject(oldValue, newValue))
    table.getSelectionModel.selectedItemProperty.onChange((_, _, newValue) => selectIssue(newValue))
  }

  def selectIssue(issue: FXBean[Issue]) {
    issue match {
      case  issue: FXBean[Issue] =>
        issueAdapter.revert()
        issueAdapter.set(Some(issue))
        detailPane.setVisible(true)
        deleteButton.setVisible(true)
      case _ =>
        issueAdapter.set()
        detailPane.setVisible(false)
        deleteButton.setVisible(false)
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

  def actionCreateIssue(event: ActionEvent) {
    val projectName = list.getSelectionModel.selectedItem.value
    val newIssue = IssueDataBase.createIssue(projectName, "New Issue")
    updateProject(projectName, projectName)
    selectIssue(newIssue)
  }

  def actionDeleteIssue(event: ActionEvent) {
    val projectName = list.getSelectionModel.selectedItem.value
    IssueDataBase.deleteIssue(issueAdapter.getBean.get.id)
    updateProject(projectName, projectName)
  }

  def actionSaveIssue(event: ActionEvent) {
    issueAdapter.clearChanges()
  }

}
