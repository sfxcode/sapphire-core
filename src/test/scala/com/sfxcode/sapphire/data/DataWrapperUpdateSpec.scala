package com.sfxcode.sapphire.data

import com.sfxcode.sapphire.data.test.TestJavaBean
import org.specs2.mutable.Specification

case class UpdateTestBean(var name: String = "test", var age: Int = 42, var description: Option[String] = Some("desc"))

class DataWrapperUpdateSpec extends Specification {

  sequential

  "FXBean" should {
    "update member value" in {
      val testBean = DataWrapper(UpdateTestBean())
      testBean.updateValue("name", "new")
      testBean.getValue("name") must be equalTo "new"
      testBean("name") must be equalTo "new"
      testBean.getOldValue("name") must be equalTo "test"
      testBean.hasChanges must beTrue
      testBean.updateValue("name", "test")
      testBean.hasChanges must beFalse
      testBean.updateValue("name", "new")
      testBean.getValue("name") must be equalTo "new"
      testBean.revert()
      testBean.getValue("name") must be equalTo "test"
    }
  }

  "FXBean" should {
    "update java member value" in {
      val bean: TestJavaBean = new TestJavaBean()
      val testBean = DataWrapper(bean)
      testBean.updateValue("name", "new")
      testBean.getValue("name") must be equalTo "new"
      testBean("name") must be equalTo "new"
      testBean.getOldValue("name") must be equalTo "test"
      testBean.hasChanges must beTrue
      testBean.updateValue("name", "test")
      testBean.hasChanges must beFalse
      testBean.updateValue("name", "new")
      testBean.getValue("name") must be equalTo "new"
      testBean.revert()
      testBean.getValue("name") must be equalTo "test"

      testBean.updateValue("age", 3)
      bean.getAge must be equalTo 3

    }
  }

}
