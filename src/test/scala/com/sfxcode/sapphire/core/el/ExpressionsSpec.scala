package com.sfxcode.sapphire.core.el

import java.text.SimpleDateFormat

import org.specs2.mutable.Specification

object CustomFunctionMapper {
  def coolMethod(s: String): String = "test-" + s
}

class ExpressionsSpec extends Specification {

  "Expressions" should {

    "registerValues" in {
      Expressions.register("ExpressionsSpec", "Test")
      Expressions.getValue("${ExpressionsSpec}") must be equalTo "Test"
    }

    "evaluate default functions" in {
      Expressions.getValue("${sf:frameworkName()}") must be equalTo "sapphire-core"

      val df = new SimpleDateFormat("yyyy-MM-dd")
      val date = df.parse("2015-01-01")

      Expressions.register("testDate", date)

      Expressions.getValue("${sf:dateString(testDate)}") must be equalTo "2015-01-01"

      Expressions.register("testBoolean", true)

      Expressions.getValue("${sf:boolString(testBoolean,'Y', 'N')}") must be equalTo "Y"

      Expressions.register("testBoolean", false)
      Expressions.getValue("${sf:boolString(testBoolean,'Y', 'N')}") must be equalTo "N"

    }

    "add custom function" in {

      val clazz: Class[_] = Class.forName("com.sfxcode.sapphire.core.el.CustomFunctionMapper")
      Expressions.context.functionMapper.addFunction("custom", "myCoolMethod", clazz, "coolMethod", classOf[String])
      Expressions.getValue("${custom:myCoolMethod('123')}") must be equalTo "test-123"

    }
  }
}
