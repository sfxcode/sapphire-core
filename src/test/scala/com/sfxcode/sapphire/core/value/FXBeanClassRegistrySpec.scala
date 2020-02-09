package com.sfxcode.sapphire.core.value

import java.time.LocalDate
import java.util.Date

import org.specs2.mutable.Specification

case class StringTest(value: String = "myString", valueOption: Option[String] = Some("myString2"))

class StringTest2 {
  var value: String = "myString"
  var date: Date = new Date()
  var localdate: LocalDate = LocalDate.now()
  var valueOption: Option[String] = Some("myString2")

  def testMethod(a: Int): Int = a + 3
}

case class LongTest(value: Long = 11, valueOption: Option[Long] = Some(12))

import com.sfxcode.sapphire.core.value.FXBeanClassRegistry._
import com.sfxcode.sapphire.core.value.PropertyType._

class FXBeanClassRegistrySpec extends Specification {

  "FXBeanClassRegistry" should {

    val stringTest = StringTest()
    val stringTest2 = new StringTest2()
    val longTest = LongTest()

    "get member info" in {

      memberInfo(stringTest, "value") must be equalTo FXBeanClassMemberInfo("value", TypeString)
      memberInfo(stringTest, "valueOption") must be equalTo FXBeanClassMemberInfo(
        "valueOption",
        TypeString,
        isOption = true)

      memberInfo(stringTest2, "value") must be equalTo FXBeanClassMemberInfo("value", TypeString)
      memberInfo(stringTest2, "valueOption") must be equalTo FXBeanClassMemberInfo(
        "valueOption",
        TypeString,
        isOption = true)
      memberInfo(stringTest2, "testMethod") must be equalTo FXBeanClassMemberInfo(
        "testMethod",
        TypeUnknown,
        isOption = false)

      memberInfo(longTest, "value") must be equalTo FXBeanClassMemberInfo(
        "value",
        TypeLong,
        isOption = false,
        classOf[java.lang.Long])
      memberInfo(longTest, "valueOption") must be equalTo FXBeanClassMemberInfo(
        "valueOption",
        TypeLong,
        isOption = true,
        classOf[java.lang.Long])
    }

    "get member info performance" in {
      // warmup and cache
      memberInfo(stringTest, "value") must be equalTo FXBeanClassMemberInfo("value", TypeString)
      memberInfo(stringTest, "valueOption") must be equalTo FXBeanClassMemberInfo(
        "valueOption",
        TypeString,
        isOption = true)

      val start = System.currentTimeMillis()
      (1 to 10000).foreach { _ =>
        memberInfo(stringTest, "value") must be equalTo FXBeanClassMemberInfo("value", TypeString)
        memberInfo(stringTest, "valueOption") must be equalTo FXBeanClassMemberInfo(
          "valueOption",
          TypeString,
          isOption = true)

      }
      val time = System.currentTimeMillis() - start

      time must be lessThan 2000
    }

    "get date member info" in {
      memberInfo(stringTest2, "date").signature must be equalTo TypeDate
      memberInfo(stringTest2, "date").javaClass.toString must contain("Date")

    }
    "get date member info" in {
      memberInfo(stringTest2, "localdate").signature must be equalTo TypeLocalDate
      memberInfo(stringTest2, "localdate").javaClass.toString must contain("LocalDate")

    }

  }
}
