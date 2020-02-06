package com.sfxcode.sapphire.core.controller

import javafx.beans.property.SimpleObjectProperty

abstract class AdditionalWindowController extends WindowController {

  override def isMainWindow: Boolean = false

  val mainWindowController = new SimpleObjectProperty[DefaultWindowController]()

  def name: String

}
