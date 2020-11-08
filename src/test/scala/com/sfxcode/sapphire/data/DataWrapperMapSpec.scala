package com.sfxcode.sapphire.data

import com.sfxcode.sapphire.data.reflect.FieldMeta
import javafx.beans.property.StringProperty
import org.specs2.mutable.Specification

import scala.collection.mutable

class DataWrapperMapSpec extends Specification {

  "DataWrapper" should {
    "update scala map value" in {
      val testMap = new mutable.HashMap[String, Any]()
      testMap.put("name", "test")
      val testBean = DataWrapper(testMap, List(FieldMeta("name")))
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
      property.isInstanceOf[StringProperty] must beTrue
      property.asInstanceOf[StringProperty].setValue("ABC")
      testMap("name") must be equalTo "ABC"
    }
  }

  "DataWrapper" should {
    "update java map value" in {
      val testMap = new java.util.HashMap[String, Any]()
      testMap.put("name", "test")
      val wrapped = DataWrapper(testMap, List(FieldMeta("name")))
      wrapped.updateValue("name", "new")
      wrapped.getValue("name") must be equalTo "new"
      testMap.get("name") must be equalTo "new"
      wrapped("name") must be equalTo "new"
      wrapped.getOldValue("name") must be equalTo "test"
      wrapped.hasChanges must beTrue
      wrapped.updateValue("name", "test")
      wrapped.hasChanges must beFalse
      wrapped.updateValue("name", "new")
      wrapped.getValue("name") must be equalTo "new"
      wrapped.revert()
      wrapped.getValue("name") must be equalTo "test"
      wrapped("name") must be equalTo "test"

      val property = wrapped.getProperty("name")
      property.isInstanceOf[StringProperty] must beTrue
      property.asInstanceOf[StringProperty].setValue("ABC")
      testMap.get("name") must be equalTo "ABC"
    }
  }

}
