package com.sfxcode.sapphire.core.controller

import com.sfxcode.sapphire.core.cdi.BeanResolver
import com.sfxcode.sapphire.core.test.TestEnvironment
import org.specs2.mutable.Specification
import org.specs2.specification.BeforeAfterAll

class MainWindowControllerSpec extends Specification with BeforeAfterAll with BeanResolver {

   def beforeAll(): Unit = {
     TestEnvironment.init()


  }

  "mainWindowController" should {

    "be valid" in {
      val appController = TestEnvironment.applicationController

      appController must not beNull

      val mainWindowController = appController.mainWindowController

      mainWindowController must not beNull

      mainWindowController.rootPane must not beNull

    }
  }

  override def afterAll(): Unit = {}
}

