package com.sfxcode.sapphire.core.value

import org.specs2.mutable.Specification


class KeyBindingsSpec extends Specification {

  "KeyBindings" should {

    "should have default constructor" in {
      val bindings = KeyBindings("name", "age")
      bindings.keys must haveSize(2)
      bindings("name") must be equalTo "name"

    }

    "should be created by class tag" in {
      val bindings = KeyBindings.forClass[TestBean]("test_")
      bindings.keys must haveSize(5)
      bindings("test_name") must be equalTo "name"
    }
  }
}
