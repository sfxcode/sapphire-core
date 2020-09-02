package com.sfxcode.sapphire.core.demo.windows

import com.sfxcode.sapphire.core.application.AbstractApplication
import com.sfxcode.sapphire.core.controller.AbstractApplicationController

object Application extends AbstractApplication {

  val applicationController: AbstractApplicationController = new ApplicationController

}
