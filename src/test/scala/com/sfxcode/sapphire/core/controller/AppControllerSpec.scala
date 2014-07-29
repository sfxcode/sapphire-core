package com.sfxcode.sapphire.core.controller

import com.sfxcode.sapphire.core.cdi.{BeanResolver, CDILauncher}
import com.sfxcode.sapphire.core.demo.test.BaseApplicationController
import com.typesafe.scalalogging.LazyLogging
import org.specs2.mutable.Specification


class AppControllerSpec extends Specification with BeanResolver with LazyLogging {

  step {
    CDILauncher.init()
  }



  "AppController" should {

    "get value of members of case class" in {
      val appController = getBean[BaseApplicationController]()
      appController must haveSuperclass[BaseApplicationController]
    }


  }


}
