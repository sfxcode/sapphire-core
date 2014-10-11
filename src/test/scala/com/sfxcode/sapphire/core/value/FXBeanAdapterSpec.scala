package com.sfxcode.sapphire.core.value

import com.sfxcode.sapphire.core.demo.appdemo.controller.WorkspaceController

import scalafx.scene.control.Label

import com.sfxcode.sapphire.core.controller.ViewController
import com.sfxcode.sapphire.core.test.{JavaFXTestEnvironment, Person, PersonDatabase}
import org.specs2.mutable.Specification
import scalafx.Includes._

class FXBeanAdapterSpec extends Specification {
  val adapter = FXBeanAdapter[Person](new WorkspaceController)
  val testPerson = PersonDatabase.testPerson(0)

  step {
    JavaFXTestEnvironment.init()
  }

  "FXBeanAdapter" should {

    "be initialized with controller" in {
      adapter.viewController must haveSuperclass[ViewController]
    }

    "be updated with FXBean" in {
      adapter.set(Some(testPerson))
      adapter.getBean.get must be equalTo testPerson.bean
    }

    "have bindings" in {
      val label = Label("test")
      label.getText must be equalTo "test"
      adapter.addBinding(label.textProperty, "name")
      adapter.set(Some(testPerson))
      label.getText must be equalTo "Cheryl Hoffman"

      label.setText("test2")
      label.getText must be equalTo "test2"

      adapter.revert()
      label.getText must be equalTo "Cheryl Hoffman"

    }

  }

}
