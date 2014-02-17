package com.sfxcode.sapphire.core.value

import org.specs2.mutable.Specification
import FXBeanClassMemberInfo._
import scala.collection.mutable
import javafx.beans.property.SimpleStringProperty

class FXBeanMapSpec extends Specification {

  sequential

  "FXBean" should {
    "update scala map value" in {
      val testMap = new mutable.HashMap[String, Any]()
      testMap.put("name", "test")
      val testBean = FXBean(testMap, List(stringInfo("name")))
      testBean.updateValue("name", "new")
      testBean.getValue("name") must be equalTo "new"
      testMap("name") must be equalTo "new"
      testBean("name") must be equalTo "new"
      testBean.getOldValue("name") must be equalTo "test"
      testBean.hasChanges must beTrue
      testBean.updateValue("name", "test")
      testBean.hasChanges must beFalse
      testBean.updateValue("name", "new")
      testBean.getValue("name") must be equalTo "new"
      testBean.revert()
      testBean.getValue("name") must be equalTo "test"
      testBean("name") must be equalTo "test"

      val property = testBean.getProperty("name")
      property.isInstanceOf[SimpleStringProperty] must beTrue
      property.asInstanceOf[SimpleStringProperty].setValue("ABC")
      testMap("name") must be equalTo "ABC"
    }
  }

  "FXBean" should {
    "update java map value" in {
      val testMap = new java.util.HashMap[String, Any]()
      testMap.put("name", "test")
      val testBean = FXBean(testMap, List(stringInfo("name")))
      testBean.updateValue("name", "new")
      testBean.getValue("name") must be equalTo "new"
      testMap.get("name") must be equalTo "new"
      testBean("name") must be equalTo "new"
      testBean.getOldValue("name") must be equalTo "test"
      testBean.hasChanges must beTrue
      testBean.updateValue("name", "test")
      testBean.hasChanges must beFalse
      testBean.updateValue("name", "new")
      testBean.getValue("name") must be equalTo "new"
      testBean.revert()
      testBean.getValue("name") must be equalTo "test"
      testBean("name") must be equalTo "test"

      val property = testBean.getProperty("name")
      property.isInstanceOf[SimpleStringProperty] must beTrue
      property.asInstanceOf[SimpleStringProperty].setValue("ABC")
      testMap.get("name") must be equalTo "ABC"
    }
  }

}
