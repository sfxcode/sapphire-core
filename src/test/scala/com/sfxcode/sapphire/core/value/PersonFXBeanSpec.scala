package com.sfxcode.sapphire.core.value

import com.sfxcode.sapphire.core.test.PersonDatabase
import org.specs2.mutable.Specification

class PersonFXBeanSpec extends Specification {

  "FXBean of Person" should {

    "get value of members of case class" in {
      val testPerson = PersonDatabase.testPerson(0)

      testPerson.bean.id must be equalTo 0
      testPerson.bean.name must be equalTo "Cheryl Hoffman"
      testPerson.bean.age must be equalTo 25

      testPerson.getValue("name") must be equalTo "Cheryl Hoffman"
      testPerson.getValue("age") must be equalTo 25

      testPerson("name") must be equalTo "Cheryl Hoffman"
      testPerson("age") must be equalTo 25
    }
  }

}
