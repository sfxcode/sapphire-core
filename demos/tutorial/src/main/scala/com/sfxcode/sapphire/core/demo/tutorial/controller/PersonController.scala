package com.sfxcode.sapphire.core.demo.tutorial.controller

import com.sfxcode.sapphire.core.fxml.FxmlLoader
import com.sfxcode.sapphire.core.value.{FXBean, FXBeanAdapter, KeyBindings}
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.TableView
import javafx.scene.layout.GridPane

@FxmlLoader(path = "/fxml/widget/Person.fxml")
class PersonController extends AbstractViewController {

  @FXML
  var tableView: TableView[FXBean[Person]] = _

  @FXML
  var editPane: GridPane = _

  lazy val adapter: FXBeanAdapter[Person] = FXBeanAdapter[Person](this)

  def items: ObservableBuffer[FXBean[Person]] = PersonFactory.personList

  override def didGainVisibilityFirstTime(): Unit = {
    super.didGainVisibilityFirstTime()

    val bindingList = List("id", "name", "age")
    val bindings = KeyBindings(bindingList)
    bindings.add("person", "Person ${_self.name()} with age of ${_self.age()}")
    adapter.addBindings(bindings)
    adapter.addConverter("age", "IntegerStringConverter")

    tableView.setItems(items)
    tableView.getSelectionModel.selectedItemProperty.onChange((_, _, newValue) => selectPerson(newValue))

    editPane.visibleProperty().bind(adapter.hasBeanProperty)
  }

  def selectPerson(person: FXBean[Person]): Unit = {
    adapter.beanProperty.value = person
    statusBarController.statusLabel.setText("%s selected".format(person.getValue("name")))
  }

  def actionRevert(event: ActionEvent): Unit = {
    adapter.revert()
  }
}
