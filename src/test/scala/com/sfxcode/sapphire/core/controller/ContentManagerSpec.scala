package com.sfxcode.sapphire.core.controller

import javafx.scene.layout.HBox

import com.sfxcode.sapphire.core.cdi.BeanResolver
import com.sfxcode.sapphire.core.demo.appdemo.controller._
import com.sfxcode.sapphire.core.scene.ContentManager
import com.sfxcode.sapphire.core.test.JavaFXTestEnvironment
import com.typesafe.scalalogging.LazyLogging
import org.specs2.mutable.Specification

import scalafx.Includes._


class ContentManagerSpec extends Specification with BeanResolver with LazyLogging {
  JavaFXTestEnvironment.init()

  val navController = new DefaultNavigationController
  val nav2Controller = new Navigation2Controller

  sequential

  step {
    navController.rootPane = new HBox()
    nav2Controller.rootPane = new HBox()

  }


  "ContentManager" should {

    "be initialized with empty controller" in {
      val cm = ContentManager(new HBox(), new MainWindowController)
      cm.updatePaneContent(navController)
      cm.actualController must be equalTo navController
    }

    "be initialized with default controller" in {
      val cm = ContentManager(new HBox(), new MainWindowController, navController)
      cm.actualController must be equalTo navController
    }

    "be updated with other controller" in {
      val cm = ContentManager(new HBox(), new MainWindowController, navController)
      cm.actualController must be equalTo navController
      cm.updatePaneContent(nav2Controller)
      cm.actualController must be equalTo nav2Controller
    }

    "return to last controller" in {
      val cm = ContentManager(new HBox(), new MainWindowController, navController)
      cm.actualController must be equalTo navController
      cm.updatePaneContent(nav2Controller)
      cm.switchToLast()
      cm.actualController must be equalTo navController
    }



  }

}
