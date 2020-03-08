package com.sfxcode.sapphire.core.showcase.controller

import com.sfxcode.sapphire.core.fxml.FxmlLoader
import com.sfxcode.sapphire.core.showcase.{ Application, ShowcaseController, ShowcaseItem }
import com.typesafe.scalalogging.LazyLogging
@FxmlLoader(path = "/com/sfxcode/sapphire/core/showcase/ShowcaseView.fxml")
class ShowcaseViewController extends ShowcaseController with LazyLogging {

  private val welcomeItem = ShowcaseItem("Welcome", "Welcome", () => getController[WelcomeController]())
  //private val formItem = ShowcaseItem("Form", "Basic Form", () => getController[FormController]())

  private val items = List(welcomeItem)

  override def didGainVisibilityFirstTime(): Unit = {
    super.didGainVisibilityFirstTime()
    infoLabel.setText(Application.title)
    updateShowcaseItems(items)
    changeShowcaseItem(welcomeItem)
  }
}
