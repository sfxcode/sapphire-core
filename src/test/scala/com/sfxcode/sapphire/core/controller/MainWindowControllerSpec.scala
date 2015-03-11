package com.sfxcode.sapphire.core.controller

import com.sfxcode.sapphire.core.cdi.{BeanResolver, CDILauncher}
import com.sfxcode.sapphire.core.demo.appdemo.BaseApplicationController
import com.sfxcode.sapphire.core.demo.appdemo.controller.MainWindowController
import com.sfxcode.sapphire.core.test.BeforeAfterAll
import org.apache.deltaspike.core.api.exclude.Exclude
import org.specs2.mutable.Specification

@Exclude
class MainWindowControllerSpec extends Specification with BeforeAfterAll with BeanResolver {
  var applicationController: BaseApplicationController = _
  var mainWindowController: MainWindowController = _

  override protected def beforeAll(): Unit = {
    CDILauncher.init()
    applicationController = getBean[BaseApplicationController]()
    mainWindowController = applicationController.mainWindowController
  }

  override protected def afterAll(): Unit = {
    CDILauncher.shutdown()
  }


  "mainWindowController" should {

    "be valid" in {
      applicationController must not beNull

      mainWindowController must not beNull

      mainWindowController.rootPane must not beNull

    }
  }

}

