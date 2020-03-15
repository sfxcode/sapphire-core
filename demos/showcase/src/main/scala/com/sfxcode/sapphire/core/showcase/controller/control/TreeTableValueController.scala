package com.sfxcode.sapphire.core.showcase.controller.control

import com.sfxcode.sapphire.core.showcase.controller.BaseController
import com.sfxcode.sapphire.core.showcase.model.{ Person, PersonDatabase }
import com.sfxcode.sapphire.core.value.{ BeanConversions, FXBean }
import javafx.fxml.FXML
import javafx.scene.control.{ TreeItem, TreeTableView }

class TreeTableValueController extends BaseController with BeanConversions {

  @FXML
  var tableView: TreeTableView[FXBean[Person]] = _

  override def didGainVisibilityFirstTime(): Unit = {
    super.didGainVisibilityFirstTime()
    val rootNode = new TreeItem[FXBean[Person]](PersonDatabase.smallPersonTable.head)
    rootNode.setExpanded(true)
    PersonDatabase.smallPersonTable.tail.foreach(person =>
      rootNode.getChildren.add(new TreeItem[FXBean[Person]](person)))
    tableView.setRoot(rootNode)
  }

}
