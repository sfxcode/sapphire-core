package com.sfxcode.sapphire.core.demo.tutorial

import com.sfxcode.sapphire.core.application.BaseApplication
import com.sfxcode.sapphire.core.controller.BaseApplicationController

object Application extends BaseApplication {

  val applicationController: BaseApplicationController = new ApplicationController

  override def height: Int = 555

  override def width: Int = 700

  override def forceMaxWidth: Boolean = true

  override def forceMaxHeight: Boolean = true
}
