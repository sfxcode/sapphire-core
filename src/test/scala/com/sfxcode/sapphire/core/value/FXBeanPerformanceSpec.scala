package com.sfxcode.sapphire.core.value

import org.specs2.mutable.Specification
import com.sfxcode.sapphire.core.cdi.CDILauncher

class FXBeanPerformanceSpec extends Specification {
  sequential

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
      println(System.currentTimeMillis()-start)
        println()

      testBean.getValue("name") must be equalTo "test"

    }
  }
}
