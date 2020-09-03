package com.sfxcode.sapphire.core.showcase.controller

import com.sfxcode.sapphire.core.fxml.FxmlLocation
import com.sfxcode.sapphire.core.showcase.bean.SimpleBeanController
import com.sfxcode.sapphire.core.showcase.controller.control.{
  TableCellController,
  TableValueController,
  TreeTableValueController
}
import com.sfxcode.sapphire.core.showcase.{ ShowcaseController, ShowcaseItem }
import com.typesafe.scalalogging.LazyLogging

class ShowcaseViewController extends ShowcaseController with LazyLogging {

  private val welcomeItem = ShowcaseItem("Welcome", "Welcome", () => getController[WelcomeController]())

  private val controlTableValueItem =
    ShowcaseItem("Control", "Table Value Factory", () => getController[TableValueController]())

  private val controlTableCellItem =
    ShowcaseItem("Control", "Table Cell Factory", () => getController[TableCellController]())

  private val controlTreeTableValueItem =
    ShowcaseItem("Control", "TreeTable", () => getController[TreeTableValueController]())

  private val beanBindingsCellItem =
    ShowcaseItem("FXBean", "Bindings", () => getController[SimpleBeanController]())

  private val items =
    List(welcomeItem, controlTableValueItem, controlTableCellItem, controlTreeTableValueItem, beanBindingsCellItem)

  override def didGainVisibilityFirstTime(): Unit = {
    super.didGainVisibilityFirstTime()
    //infoLabel.setText(Application.title)
    updateShowcaseItems(items)
    changeShowcaseItem(welcomeItem)
  }
}
