package com.sfxcode.sapphire.core.el

import java.text.SimpleDateFormat

import org.specs2.mutable.Specification

class ExpressionsSpec extends Specification {

  "Expressions" should {

    "registerValues" in {
      Expressions.register("ExpressionsSpec", "Test")
      Expressions.getValue("${ExpressionsSpec}") must be equalTo "Test"
    }

    "evaluate default functions" in {
      Expressions.getValue("${sf:frameworkName()}") must be equalTo "Sapphire"

      val df = new SimpleDateFormat("yyyy-MM-dd")
      val date = df.parse("2015-01-01")

      Expressions.register("testDate", date)

      Expressions.getValue("${sf:dateString(testDate)}") must be equalTo "2015-01-01"

      Expressions.register("testBoolean", true)

      Expressions.getValue("${sf:boolString(testBoolean,'Y', 'N')}") must be equalTo "Y"

      Expressions.register("testBoolean", false)
      Expressions.getValue("${sf:boolString(testBoolean,'Y', 'N')}") must be equalTo "N"

    }

  }
}
