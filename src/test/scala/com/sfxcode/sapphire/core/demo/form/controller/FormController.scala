package com.sfxcode.sapphire.core.demo.form.controller

import com.sfxcode.sapphire.core.controller.ViewController
import com.sfxcode.sapphire.core.value.{FXBeanAdapter, KeyBindings}
import com.sfxcode.sapphire.core.demo.form.model.Person
import com.sfxcode.sapphire.core.Includes._


class FormController extends ViewController {
  lazy val formAdapter = FXBeanAdapter[Person](this)

  val person = Person()

  override def didGainVisibility() {
    super.didGainVisibility()
    val bindingList = List("name", "age", "description", "active")
    val bindings = KeyBindings(bindingList, "form1_")
    bindings.add(bindingList, "form2_")
    bindings.add("person", "Person ${_self.name()} with age of ${_self.age()} is active: ${_self.active()}")
    formAdapter.addBindings(bindings)
    formAdapter.addConverter("form1_age", "IntegerStringConverter")
    formAdapter.addConverter("form2_age", "IntegerStringConverter")
    formAdapter.addConverter("form2_active", "BooleanStringConverter")
    formAdapter.set(person)
  }
}
