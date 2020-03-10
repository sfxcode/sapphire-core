package com.sfxcode.sapphire.core.showcase.bean

import com.sfxcode.sapphire.core.showcase.controller.BaseController
import com.sfxcode.sapphire.core.showcase.model.{ Person, PersonDatabase }
import com.sfxcode.sapphire.core.value.{ BeanConversions, FXBean, FXBeanAdapter, KeyBindings }

import scala.util.Random

class SimpleBeanController extends BaseController with BeanConversions {
  val random = new Random()
  val RandomRange = 100

  lazy val adapter: FXBeanAdapter[Person] = FXBeanAdapter[Person](this)

  override def didGainVisibilityFirstTime(): Unit = {
    super.didGainVisibilityFirstTime()
    val bindings = KeyBindings()
    bindings.add(List("name", "age", "address", "isActive"))
    bindings.add("person", "Person ${_self.name()} with age of ${_self.age()} is active: ${_self.isActive()}")
    adapter.addBindings(bindings)
    adapter.addConverter("age", "IntegerStringConverter")
    adapter.addConverter("isActive", "BooleanStringConverter")

  }

  override def didGainVisibility() {
    super.didGainVisibility()
    setRandomPerson()
  }

  private def setRandomPerson(): Unit = {
    val person: FXBean[Person] = PersonDatabase.testPerson(random.nextInt(RandomRange))
    adapter.set(person)
  }

}
