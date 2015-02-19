package com.sfxcode.sapphire.core.test

import com.sfxcode.sapphire.core.cdi.{CDILauncher, BeanResolver}
import com.sfxcode.sapphire.core.demo.appdemo.BaseApplicationController
import com.sfxcode.sapphire.core.demo.appdemo.controller.MainWindowController
import org.specs2.mutable.BeforeAfter

trait cdi extends BeforeAfter with BeanResolver {
  var application: BaseApplicationController = null
  var mainWindowController: MainWindowController = null

  def before = {
    CDILauncher.init()
    application = getBean[BaseApplicationController]()
    mainWindowController = application.mainWindowController
  }

  def after = {
    CDILauncher.shutdown()
  }


}
