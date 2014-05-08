package com.sfxcode.sapphire.core.demo.test.controller

import scalafx.scene.control.ListView
import com.sfxcode.sapphire.core.controller.ViewController
import com.sfxcode.sapphire.core.demo.test.model.{PersonFactory, Person}
import com.sfxcode.sapphire.core.value.FXBeanCollections._
import com.sfxcode.sapphire.core.value.FXBean
import com.sfxcode.sapphire.core.Includes._
import scalafxml.core.macros.sfxml

class WorkspaceController extends ViewController  {
  def ui = fxml.asInstanceOf[WorkspaceFxml]

  val personList = observableBuffer[Person]

  var name:String=""

  override def didGainVisibilityFirstTime() {
    super.willGainVisibility()
    PersonFactory.personList.foreach(p => personList.+=(p))
    ui.listView.setItems(personList)
  }

  override def didGainVisibility() {
    super.didGainVisibility()
  }

}

@sfxml
class WorkspaceFxml(val listView: ListView[FXBean[Person]] )
