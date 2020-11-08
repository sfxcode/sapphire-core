package com.sfxcode.sapphire.data.el

import org.specs2.mutable.Specification

object CustomFunctionMapper {
  def coolMethod(s: String): String = "test-" + s
}

class ExpressionsSpec extends Specification {

  "Expressions" should {

    "registerValues" in {
      Expressions.register("DefaultFunctionsSpec", "Test")
      Expressions.getValue("${DefaultFunctionsSpec}").get must be equalTo "Test"
    }

    // #customFunction
    "add custom function" in {

      val clazz: Class[_] = Class.forName("com.sfxcode.sapphire.data.el.CustomFunctionMapper")
      Expressions.functionHelper.addFunction("custom", "myCoolMethod", clazz, "coolMethod", classOf[String])
      Expressions.getValue("${custom:myCoolMethod('123')}").get must be equalTo "test-123"

    }
    // #customFunction

  }
}
