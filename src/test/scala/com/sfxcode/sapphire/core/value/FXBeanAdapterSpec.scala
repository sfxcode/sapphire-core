package com.sfxcode.sapphire.core.value

import com.sfxcode.sapphire.core.controller.ViewController
import com.sfxcode.sapphire.core.demo.appdemo.controller.WorkspaceController
import com.sfxcode.sapphire.core.test.{Person, PersonDatabase}
import org.specs2.mutable.Specification

class FXBeanAdapterSpec extends Specification {
  val adapter = FXBeanAdapter[Person](new WorkspaceController)
  val testPerson = PersonDatabase.testPerson(0)

  "FXBeanAdapter" should {

    "be initialized with controller" in {
      adapter.viewController must haveSuperclass[ViewController]
    }

    "be updated with FXBean" in {
      adapter.set(Some(testPerson))
      adapter.getBean.get must be equalTo testPerson.bean
    }


  }

}
