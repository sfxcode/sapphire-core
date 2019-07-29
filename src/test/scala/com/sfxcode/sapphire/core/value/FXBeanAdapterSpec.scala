package com.sfxcode.sapphire.core.value

import com.sfxcode.sapphire.core.controller.ViewController
import com.sfxcode.sapphire.core.test.{Person, PersonDatabase, TestViewController}
import org.specs2.mutable.Specification

class FXBeanAdapterSpec extends Specification {
  val adapter = FXBeanAdapter[Person](new TestViewController)
  val testPerson = PersonDatabase.testPerson(0)

  "FXBeanAdapter" should {

    "be initialized with controller" in {
      adapter.viewController must haveSuperclass[ViewController]
    }

    //    "be updated with FXBean" in {
    //      adapter.hasBeanProperty.getValue must beFalse
    //      adapter.beanProperty.getValue = testPerson
    //      adapter.hasBeanProperty.getValue must beTrue
    //    }
  }

}
