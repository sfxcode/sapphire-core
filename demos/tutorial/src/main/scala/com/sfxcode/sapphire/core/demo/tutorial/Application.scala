package com.sfxcode.sapphire.core.demo.tutorial

import com.sfxcode.sapphire.core.application.AbstractApplication
import com.sfxcode.sapphire.core.controller.AbstractApplicationController

object Application extends AbstractApplication {

  val applicationController: AbstractApplicationController = new ApplicationController

  override def height: Int = 555

  override def width: Int = 700

  override def forceMaxWidth: Boolean = true

  override def forceMaxHeight: Boolean = true
}
