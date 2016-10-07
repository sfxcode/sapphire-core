package com.sfxcode.sapphire.core.value

import com.sfxcode.sapphire.core.test.PersonDatabase
import org.specs2.mutable.Specification

class PersonenFXBeanSpec extends Specification {

  "Personen List of FXBean" should {

    "should filter by FXBeanFunction" in {
      val testPersonen = PersonDatabase.testPersonen

      testPersonen.size must be equalTo 200
      val filtered = testPersonen.filter(p => p.getValue("age") == 25)
      filtered.size must be equalTo 13

    }
  }

}
