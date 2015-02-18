package com.sfxcode.sapphire.core.value

import org.apache.deltaspike.core.api.exclude.Exclude

import com.typesafe.scalalogging.LazyLogging
import org.specs2.mutable.Specification

@Exclude
class FXBeanPerformanceSpec extends Specification with LazyLogging {


  "FXBean" should {

    "get value of members of case class" in {
      val testBean = FXBean[TestClass](new TestClass())
      testBean.getValue("name") must be equalTo "test"
      testBean.getValue("age") must be equalTo 42
      val max = 1000
      val start = System.currentTimeMillis()
      (1 to max).foreach(i => {
        testBean.getValue("name")
        testBean.getValue("age")
        testBean.updateValue("name", "test")
        testBean.updateValue("age", 3)
      } )
      logger.debug("" + (System.currentTimeMillis()-start))

      testBean.getValue("name") must be equalTo "test"

    }
  }
}
