package com.sfxcode.sapphire.core.controller

import com.sfxcode.sapphire.core.cdi.BeanResolver
import com.sfxcode.sapphire.core.demo.appdemo.BaseApplicationController
import com.sfxcode.sapphire.core.test.JavaFXTestEnvironment
import com.typesafe.scalalogging.LazyLogging
import org.specs2.mutable.Specification


class AppControllerSpec extends Specification with BeanResolver with LazyLogging {
  JavaFXTestEnvironment.init()

  sequential

  "AppController" should {

    "get value of members of case class" in {

      val appController = getBean[BaseApplicationController]()
      appController must haveSuperclass[BaseApplicationController]


    }


  }


}
