package com.sfxcode.sapphire.core.controller

import com.sfxcode.sapphire.core.cdi.BeanResolver
import com.sfxcode.sapphire.core.demo.appdemo.controller.DefaultNavigationController
import com.sfxcode.sapphire.core.test.TestEnvironment
import org.specs2.mutable.Specification

class MainWindowControllerSpec extends Specification with BeanResolver {
  TestEnvironment.init()

  def appController = TestEnvironment.applicationController
  def mainWindowController = appController.mainWindowController

  "mainWindowController" should {

    "be valid" in {

      appController must not beNull

      mainWindowController must not beNull

      mainWindowController.rootPane must not beNull
    }

    "have a navigation manager" in {

      mainWindowController.navigationManager.actualController must beAnInstanceOf[DefaultNavigationController]

    }


  }

}

