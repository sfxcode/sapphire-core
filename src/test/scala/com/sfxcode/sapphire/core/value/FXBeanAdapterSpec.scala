package com.sfxcode.sapphire.core.value

import com.sfxcode.sapphire.core.controller.ViewController
import com.sfxcode.sapphire.core.test.{ Person, PersonDatabase, TestViewController }
import org.specs2.mutable.Specification

class FXBeanAdapterSpec extends Specification {
  val adapter = FXBeanAdapter[Person](new TestViewController)

  sequential

  "FXBeanAdapter" should {

    "be initialized with controller" in {
      adapter.viewController must haveSuperclass[ViewController]
    }

    "be updated with FXBean" in {
      val testPerson = PersonDatabase.testPerson(0)
      adapter.hasBeanProperty.get must beFalse
      adapter.set(testPerson)
      adapter.hasBeanProperty.get must beTrue
    }
  }

}
