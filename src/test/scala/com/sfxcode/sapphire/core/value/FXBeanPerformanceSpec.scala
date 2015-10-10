package com.sfxcode.sapphire.core.value

import com.typesafe.scalalogging.LazyLogging
import org.scalameter._
import org.specs2.mutable.Specification

class FXBeanPerformanceSpec extends Specification with LazyLogging {


  "FXBean" should {

    "get value of members of case class" in {
      val testBean = FXBean[TestClass](new TestClass())
      testBean.getValue("name") must be equalTo "test"
      testBean.getValue("age") must be equalTo 42
      val max = 1000
      val time = measure {
        (1 to max).foreach(i => {
          testBean.getValue("name")
          testBean.getValue("age")
          testBean.updateValue("name", "test")
          testBean.updateValue("age", 3)
        })
      }.value

      time must be lessThan 250

    }
  }
}
