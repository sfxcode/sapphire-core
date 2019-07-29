package com.sfxcode.sapphire.core.value

import com.sfxcode.sapphire.core.test.{Book, PersonDatabase}
import com.typesafe.scalalogging.LazyLogging
import javafx.beans.property._
import org.apache.deltaspike.core.api.exclude.Exclude
import org.specs2.mutable._

case class Zip(value: Long = 12345)

case class TestBean(name: String = "test", age: Int = 42, zip: Zip = Zip(), description: Option[String] = Some("desc"), observable: Property[_] = new SimpleStringProperty("observable")) {
  def doubleAge() = age * 2

  def multiply(first: Int, second: Int): Int = first * second

}

class TestClass(var name: String = "test", var age: Int = 42, var zip: Zip = Zip(), var description: Option[String] = Some("desc"), var observable: Property[_] = new SimpleStringProperty("observable")) {
  def doubleAge() = age * 2

  def multiply(first: Int, second: Int): Int = first * second

}

case class ChildBean(childName: String = "childName") {
  def fullChildName(): String = "[child] %s".format(childName)
}

case class ParentBean(parentName: String = "parentName", child: ChildBean = ChildBean()) {
  def fullName(): String = "%s : %s".format(parentName, child.fullChildName())
}

case class ListClass(list: List[String] = List("A", "B"))

@Exclude
class FXBeanSpec extends Specification with LazyLogging {

  sequential

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

    "update expressions" in {
      val testBean = FXBean[TestBean](TestBean())
      val observableAge = testBean.getIntegerProperty("${_self.age()}")
      val observable = testBean.getIntegerProperty("${_self.doubleAge()}")
      observableAge.getValue must be equalTo 42
      observable.getValue must be equalTo 84

      testBean.updateValue("age", 40)
      // only member expressions updated by default
      observableAge.getValue must be equalTo 40
      observable.getValue must be equalTo 80 //

    }

    "update child expressions" in {
      val testBean = FXBean[ParentBean](ParentBean())
      val observableName = testBean.getStringProperty("${_self.fullName()}")
      observableName.getValue must be equalTo "parentName : [child] childName"
      testBean.updateValue("parentName", "parent")
      observableName.getValue must be equalTo "parent : [child] childName"
      testBean.updateValue("child.childName", "child")
      observableName.getValue must be equalTo "parent : [child] child"

    }

    "get observable property" in {
      val testBean = FXBean[TestBean](TestBean())

      val observable = testBean.getIntegerProperty("age")
      observable.getValue must be equalTo 42

    }

    "get observable expression property" in {
      val testBean = FXBean[ListClass](ListClass())

      val observable = testBean.getIntegerProperty("${_self.list().size()}")
      observable.getValue must be equalTo 2

      val observableString = testBean.getStringProperty("List count: ${_self.list().size()}")
      observableString.getValue must be equalTo "List count: 2"

    }

    "handle complex case classes" in {
      val book = FXBean[Book](PersonDatabase.scalaBook)
      book.getValue("title") must be equalTo "Programming In Scala"
      book.getValue("author.name") must be equalTo "Martin Odersky"
      book.updateValue("author.name", "M. Odersky")
      book.getValue("author.name") must be equalTo "M. Odersky"

      val observable = book.getStringProperty("author.name")
      observable.getValue must be equalTo "M. Odersky"

      observable.set("Martin Odersky")
      book.getValue("author.name") must be equalTo "Martin Odersky"
    }

    "handle scala map " in {
      val map = Map[String, Any]("test" -> 3, "test.test" -> 4)

      val book = FXBean[Map[String, Any]](map)

      book.getValue("test") must be equalTo 3
      book.getValue("test.test") must be equalTo 4

    }

    "handle changes in " in {
      val testBean = FXBean[ParentBean](ParentBean())

      testBean.hasChanges must beFalse

      testBean.updateValue("parentName", "newName")
      testBean.getValue("parentName") must be equalTo "newName"
      testBean.hasChanges must beTrue

      testBean.revert()
      testBean.getValue("parentName") must be equalTo "parentName"
      testBean.hasChanges must beFalse

      testBean.updateValue("child.childName", "newName")
      testBean.getValue("child.childName") must be equalTo "newName"
      testBean.hasChanges must beTrue
      testBean.updateValue("parentName", "newName")
      testBean.updateValue("parentName", "parentName")
      testBean.hasChanges must beTrue

      testBean.revert()
      testBean.getValue("child.childName") must be equalTo "childName"

      testBean.hasChanges must beFalse
    }
  }

}
