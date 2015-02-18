package com.sfxcode.sapphire.core.controller

import com.sfxcode.sapphire.core.cdi.{BeanResolver, CDILauncher}
import com.sfxcode.sapphire.core.demo.appdemo.BaseApplicationController
import com.sfxcode.sapphire.core.demo.appdemo.controller.MainWindowController
import org.apache.deltaspike.core.api.exclude.Exclude
import org.specs2.mutable.{Specification, _}

@Exclude
class MainWindowControllerSpec extends Specification {

  "mainWindowController" should {

    "be valid" in new cdi {

      application must not beNull

      mainWindowController must not beNull

      3 must be equalTo(3)
    }
  }

}

trait cdi extends Before with BeanResolver {
  var application: BaseApplicationController = null
  var mainWindowController: MainWindowController = null

  def before = {
    CDILauncher.init()
    application = getBean[BaseApplicationController]()
    mainWindowController = application.mainWindowController
    CDILauncher.shutdown()
  }

}
