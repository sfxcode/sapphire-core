package com.sfxcode.sapphire.core.controller

import javafx.scene.layout.HBox

import com.sfxcode.sapphire.core.cdi.{CDILauncher, BeanResolver}
import com.sfxcode.sapphire.core.demo.test.controller.{DefaultNavigationController, MainWindowController}
import com.sfxcode.sapphire.core.scene.ContentManager
import com.typesafe.scalalogging.LazyLogging
import org.specs2.mutable.Specification
import scalafx.Includes._


class ContentManagerSpec extends Specification with BeanResolver with LazyLogging {
  sequential

  step {
    CDILauncher.init()
  }


  "ContentManager" should {

    "be initialized with default controller" in {
      val navController = new DefaultNavigationController
      navController.rootPane = new HBox()
      val cm = ContentManager(new HBox(), new MainWindowController, navController)

      cm.actualController must be equalTo navController
    }

  }

}
