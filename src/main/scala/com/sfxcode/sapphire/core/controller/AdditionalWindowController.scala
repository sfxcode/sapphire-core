package com.sfxcode.sapphire.core.controller

import javafx.beans.property.SimpleObjectProperty

abstract class AdditionalWindowController extends WindowController {

  val mainWindowController = new SimpleObjectProperty[WindowController]()

  override def isMainWindow: Boolean = false

}
