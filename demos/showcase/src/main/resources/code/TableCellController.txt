package com.sfxcode.sapphire.core.showcase.controller.control

import com.sfxcode.sapphire.core.showcase.controller.BaseController
import com.sfxcode.sapphire.core.showcase.model.{ Person, PersonDatabase }
import com.sfxcode.sapphire.core.value.{ BeanConversions, FXBean }
import javafx.fxml.FXML
import javafx.scene.control.TableView

class TableCellController extends BaseController with BeanConversions {

  @FXML
  var tableView: TableView[FXBean[Person]] = _

  override def didGainVisibilityFirstTime(): Unit = {
    super.didGainVisibilityFirstTime()
    tableView.setItems(PersonDatabase.smallPersonTable)
  }

}
