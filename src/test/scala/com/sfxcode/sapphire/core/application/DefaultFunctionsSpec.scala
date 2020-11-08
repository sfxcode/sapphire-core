package com.sfxcode.sapphire.core.application

import java.text.SimpleDateFormat

import com.sfxcode.sapphire.data.el.Expressions
import org.specs2.mutable.Specification

class DefaultFunctionsSpec extends Specification {

  "Expressions" should {

    "evaluate default functions" in {
      val functionHelper = Expressions.functionHelper
      DefaultFunctions.addDefaultFunctions(functionHelper)

      Expressions.getValue("${sf:frameworkName()}").get must be equalTo "sapphire-core"

      val df = new SimpleDateFormat("yyyy-MM-dd")
      val date = df.parse("2015-01-01")

      Expressions.register("testDate", date)

      Expressions.getValue("${sf:dateString(testDate)}").get must be equalTo "2015-01-01"

      val nowString = Expressions.getValue("${sf:nowAsString()}").get.toString

      nowString must not beEmpty

      Expressions.register("testBoolean", true)

      Expressions.getValue("${sf:boolString(testBoolean,'Y', 'N')}").get must be equalTo "Y"

      // #coreFunction
      Expressions.register("testBoolean", false)
      Expressions.getValue("${sf:boolString(testBoolean,'Y', 'N')}").get must be equalTo "N"
      // #coreFunction

      Expressions.getValue("${sf:configString('test.string')}").get must be equalTo "Hello World"

    }

  }
}
