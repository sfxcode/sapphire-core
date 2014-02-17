package com.sfxcode.sapphire.core.demo.base.controller

import com.sfxcode.sapphire.core.controller.ViewController
import javafx.fxml.FXML
import javafx.scene.control.ListView
import com.sfxcode.sapphire.core.demo.base.model.{PersonFactory, Person}
import com.sfxcode.sapphire.core.value.FXBeanCollections._
import com.sfxcode.sapphire.core.value.{FXBeanCollections, FXBean}
import com.sfxcode.sapphire.core.Includes._

class WorkspaceController extends ViewController  {

  @FXML
  var listView: ListView[FXBean[Person]] = _

  val personList = observableBuffer[Person]

  var name:String=""

  override def willGainVisibility() {
    super.willGainVisibility()
    PersonFactory.personList.foreach(p => personList.+=(p))
    listView.setItems(personList)
  }

  override def didGainVisibility() {
    super.didGainVisibility()
  }

}
