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

case class IntTest(value: Int = 11, valueOption: Option[Int] = Some(12))
case class LongTest(value: Long = 11, valueOption: Option[Long] = Some(12))
case class FloatTest(value: Float = 11.0f, valueOption: Option[Float] = Some(12.0f))
case class DoubleTest(value: Double = 11.0, valueOption: Option[Double] = Some(12.0))
case class BooleanTest(value: Boolean = false, valueOption: Option[Boolean] = Some(true))
case class DateTest(value: Date = new Date(), valueOption: Option[Date] = Some(new Date()))
case class LocalDateTest(value: LocalDate = LocalDate.now(), valueOption: Option[LocalDate] = Some(LocalDate.now()))

case class NoneTest(valueOption: Option[Long] = None)

import com.sfxcode.sapphire.data.reflect.FieldMetaRegistry._
import com.sfxcode.sapphire.data.reflect.PropertyType._

class FXBeanClassRegistrySpec extends Specification {

  "FXBeanClassRegistry" should {

    val stringTest = StringTest()
    val stringTest2 = new StringTest2()
    val intTest = IntTest()
    val longTest = LongTest()
    val floatTest = FloatTest()
    val doubleTest = DoubleTest()
    val booleanTest = BooleanTest()
    val dateTest = DateTest()
    val localDateTest = LocalDateTest()
    val noneTest = NoneTest()

    def testMemberInfo(target: AnyRef, name: String, signature: PropertyValue, isOption: Boolean): Boolean = {
      val info = fieldMeta(target, name)
      info.name must be equalTo name
      info.signature must be equalTo signature
      info.isOption must be equalTo isOption
      true

    }

    "get member info for String" in {

      testMemberInfo(stringTest, "value", TypeString, isOption = false) must beTrue
      testMemberInfo(stringTest, "valueOption", TypeString, isOption = true) must beTrue

      testMemberInfo(stringTest2, "value", TypeString, isOption = false) must beTrue
      testMemberInfo(stringTest2, "valueOption", TypeString, isOption = true) must beTrue

    }

    "get member info for Int" in {

      testMemberInfo(intTest, "value", TypeInt, isOption = false) must beTrue
      testMemberInfo(intTest, "valueOption", TypeInt, isOption = true) must beTrue

    }

    "get member info for Long" in {
      testMemberInfo(longTest, "value", TypeLong, isOption = false) must beTrue
      testMemberInfo(longTest, "valueOption", TypeLong, isOption = true) must beTrue

    }

    "get member info for Long with None" in {

      testMemberInfo(noneTest, "valueOption", TypeObject, isOption = true) must beTrue
    }

    "get member info for Float" in {

      testMemberInfo(floatTest, "value", TypeFloat, isOption = false) must beTrue
      testMemberInfo(floatTest, "valueOption", TypeFloat, isOption = true) must beTrue

    }

    "get member info for Double" in {

      testMemberInfo(doubleTest, "value", TypeDouble, isOption = false) must beTrue
      testMemberInfo(doubleTest, "valueOption", TypeDouble, isOption = true) must beTrue

    }

    "get member info for Boolean" in {

      testMemberInfo(booleanTest, "value", TypeBoolean, isOption = false) must beTrue
      testMemberInfo(booleanTest, "valueOption", TypeBoolean, isOption = true) must beTrue

    }

    "get member info for Date" in {

      testMemberInfo(dateTest, "value", TypeDate, isOption = false) must beTrue
      testMemberInfo(dateTest, "valueOption", TypeDate, isOption = true) must beTrue

    }

    "get member info for LocalDate" in {

      testMemberInfo(localDateTest, "value", TypeLocalDate, isOption = false) must beTrue
      testMemberInfo(localDateTest, "valueOption", TypeLocalDate, isOption = true) must beTrue
    }

    "get member info performance" in {
      // warmup and cache
      testMemberInfo(stringTest, "value", TypeString, isOption = false) must beTrue
      testMemberInfo(stringTest, "valueOption", TypeString, isOption = true) must beTrue

      val start = System.currentTimeMillis()
      (1 to 10000).foreach { _ =>
        testMemberInfo(stringTest, "value", TypeString, isOption = false) must beTrue
        testMemberInfo(stringTest, "valueOption", TypeString, isOption = true) must beTrue
      }
      val time = System.currentTimeMillis() - start

      time must be lessThan 2000
    }

  }
}
