package com.sfxcode.sapphire.core.demo.tutorial.controller

import com.sfxcode.sapphire.core.demo.tutorial.model.{Person, PersonFactory}
import com.sfxcode.sapphire.core.fxml.FxmlLoader
import com.sfxcode.sapphire.core.value.{BeanConversions, FXBean, FXBeanAdapter, KeyBindings}
import javafx.collections.ObservableList
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.TableView
import javafx.scene.layout.VBox

@FxmlLoader(path = "/fxml/widget/Person.fxml")
class PersonController extends AbstractViewController with BeanConversions {

  @FXML
  var tableView: TableView[FXBean[Person]] = _

  // #adapter_create
  @FXML
  var personBox: VBox = _

  // second parameter parent Node is optional,
  // but sometimes needed for the correct NodeLocator lookup
  lazy val adapter: FXBeanAdapter[Person] = FXBeanAdapter[Person](this, personBox)

  // #adapter_create

  def items: ObservableList[FXBean[Person]] = PersonFactory.personList

  // #bindings
  override def didGainVisibilityFirstTime(): Unit = {
    super.didGainVisibilityFirstTime()

    // #bindingList #labels
    val bindings = KeyBindings("id", "name", "age", "test")
    // Expression Binding Example
    bindings.add("person", "Person ${_self.name()} with age of ${_self.age()}")

    adapter.addBindings(bindings)
    // Converter Example
    adapter.addIntConverter("age")
    // #bindingList #labels

    tableView.setItems(items) // #labels
    tableView.getSelectionModel.selectedItemProperty.addListener((_, _, newValue) => selectPerson(newValue)) // #labels
    personBox.visibleProperty().bind(adapter.hasBeanProperty)
  }

  // #bindings

  // #adapter_use
  def selectPerson(person: FXBean[Person]): Unit = {
    adapter.set(person)
    statusBarController.statusLabel.setText("%s selected".format(person.getValue("name"))) // #labels
  }

  def actionRevert(event: ActionEvent): Unit = {
    adapter.revert()
  }

  // #adapter_use

}
