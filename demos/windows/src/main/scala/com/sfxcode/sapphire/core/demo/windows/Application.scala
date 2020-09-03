package com.sfxcode.sapphire.core.demo.windows

import com.sfxcode.sapphire.core.application.BaseApplication
import com.sfxcode.sapphire.core.controller.BaseApplicationController

object Application extends BaseApplication {

  val applicationController: BaseApplicationController = new ApplicationController

}
