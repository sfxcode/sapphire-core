package com.sfxcode.sapphire.core.controller

import scalafx.beans.property.ObjectProperty

abstract class AdditionalWindowController extends WindowController {

  val mainWindowController = new ObjectProperty[WindowController]()

  override def isMainWindow:Boolean = false

}
