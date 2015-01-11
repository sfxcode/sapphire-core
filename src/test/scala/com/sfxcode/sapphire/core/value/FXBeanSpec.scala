package com.sfxcode.sapphire.core.value

import com.sfxcode.sapphire.core.cdi.CDILauncher
import com.typesafe.scalalogging.LazyLogging
import org.specs2.mutable._

import scalafx.beans.property._

case class Zip(value: Long = 12345)

case class TestBean(name: String = "test", age: Int = 42, zip: Zip = Zip(), description: Option[String] = Some("desc"), observable: Property[_, _] = new StringProperty("observable")) {
  def doubleAge() = age * 2

  def multiply(first: Int, second: Int): Int = first * second

}

class TestClass(var name: String = "test", var age: Int = 42, var zip: Zip = Zip(), var description: Option[String] = Some("desc"), var observable: Property[_, _] = new StringProperty("observable")) {
  def doubleAge() = age * 2

  def multiply(first: Int, second: Int): Int = first * second

}


class FXBeanSpec extends Specification with LazyLogging {

  sequential

  step {
    CDILauncher.init()
  }

  "FXBean" should {

    "get value of members of case class" in {
      val testBean = FXBean[TestBean](TestBean())
      testBean.getValue("name") must be equalTo "test"
      testBean.getValue("age") must be equalTo 42
      testBean.getValue("description") must be equalTo Some("desc")
      testBean.getValue("observable").asInstanceOf[StringProperty].getValue must be equalTo "observable"
      testBean.getValue("zip").asInstanceOf[Zip].value must be equalTo 12345

      testBean("name") must be equalTo "test"
      testBean("age") must be equalTo 42
      testBean("description") must be equalTo Some("desc")
      testBean("observable").asInstanceOf[StringProperty].getValue must be equalTo "observable"
      testBean("zip").asInstanceOf[Zip].value must be equalTo 12345

      val testBean2 = FXBean[TestBean](TestBean())

      testBean2.updateValue("description", None)
      testBean2.bean.description must beNone
      testBean2.getValue("description") must be equalTo None
    }

    "get value of members of class" in {
      val testBean = FXBean[TestClass](new TestClass())
      testBean.getValue("name") must be equalTo "test"
      testBean.getValue("age") must be equalTo 42
      testBean.getValue("description") must be equalTo Some("desc")
      testBean.getValue("observable").asInstanceOf[StringProperty].getValue must be equalTo "observable"
      testBean.getValue("zip").asInstanceOf[Zip].value must be equalTo 12345

      testBean("name") must be equalTo "test"
      testBean("age") must be equalTo 42
      testBean("description") must be equalTo Some("desc")
      testBean("observable").asInstanceOf[StringProperty].getValue must be equalTo "observable"
      testBean("zip").asInstanceOf[Zip].value must be equalTo 12345
    }

    "get value of members of java class" in {
      val bean: TestJavaBean = new TestJavaBean()
      val testBean = FXBean[TestJavaBean](bean)
      logger.debug(testBean.getProperty("date").toString())
      testBean.getValue("name") must be equalTo "test"
      testBean.getValue("age") must be equalTo 42

    }


    "evaluate expressions" in {
      val testBean = FXBean[TestBean](TestBean())
      testBean.getValue("result ${2*4}") must be equalTo "result 8"
      testBean.getValue("${_self.description().get()}") must be equalTo "desc"
      testBean.getValue("!{_self.description().get()}") must be equalTo "desc"
      testBean.getValue("zip.value") must be equalTo 12345
      testBean.getValue("${_self.age() / 2}") must be equalTo 21.0
      testBean.getValue("${_self.multiply(2,3)}") must be equalTo 6
      testBean.getValue("!{_self.multiply(2,3)}") must be equalTo 6
      testBean.getValue("doubleAge()") must be equalTo 84
    }

    "get observable property" in {
      val testBean = FXBean[TestBean](TestBean())

      val observable = testBean.getProperty("age")
      observable.asInstanceOf[IntegerProperty].getValue must be equalTo 42

    }
  }

}
