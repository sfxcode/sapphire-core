package com.sfxcode.sapphire.core.showcase.controller.control

import com.sfxcode.sapphire.core.showcase.controller.BaseController
import com.sfxcode.sapphire.core.showcase.model.{ Person, PersonDatabase }
import com.sfxcode.sapphire.core.value.{ BeanConversions, FXBean }
import javafx.fxml.FXML
import javafx.scene.control.{ Control, TableView }

import scala.util.Random

class TableValueController extends BaseController {
  val random = new Random()
  val RandomRange = 10

  @FXML
  var tableView: TableView[FXBean[Person]] = _

  override def didGainVisibilityFirstTime(): Unit = {
    super.didGainVisibilityFirstTime()
    tableView.setItems(PersonDatabase.smallPersonTable)
    tableView.getSelectionModel.selectedItemProperty.addListener((_, _, newValue) => selectPerson(newValue))
  }

  def selectPerson(person: FXBean[Person]): Unit =
    logger.info("%s selected".format(person.getValue("name")))

  def testString: String = "Test " + (random.nextInt(RandomRange) + 1)

}
