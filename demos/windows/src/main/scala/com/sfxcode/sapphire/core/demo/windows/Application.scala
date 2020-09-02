package com.sfxcode.sapphire.core.demo.windows

import com.sfxcode.sapphire.core.application.FXApp
import com.sfxcode.sapphire.core.controller.DefaultWindowController

object Application extends FXApp {

  val applicationController: DefaultWindowController = beanResolver.getBean[ApplicationController]()

}
