package com.sfxcode.sapphire.core.value

import org.specs2.mutable.Specification
import com.sfxcode.sapphire.core.cdi.CDILauncher

case class StringTest(value: String = "myString", valueOption: Option[String] = Some("myString2"))

class StringTest2  {
  var value: String = "myString"
  var valueOption: Option[String] = Some("myString2")

  def testMethod(a: Int): Int = a + 3
}

case class LongTest(value: Long = 11, valueOption: Option[Long] = Some(12))

import FXBeanClassRegistry._
import PropertyType._

class FXBeanClassRegistrySpec extends Specification {
  sequential

  "FXBeanClassRegistry" should {

    val stringTest = StringTest()
    val stringTest2 = new StringTest2()
    val longTest = LongTest()


    "get member info" in {
      (1 to 1000).foreach(_ => {
        memberInfo(stringTest, "value") must be equalTo FXBeanClassMemberInfo("value", TypeString)
        memberInfo(stringTest, "valueOption") must be equalTo FXBeanClassMemberInfo("valueOption", TypeString, isOption = true)

      })
        memberInfo(stringTest, "value") must be equalTo FXBeanClassMemberInfo("value", TypeString)
        memberInfo(stringTest, "valueOption") must be equalTo FXBeanClassMemberInfo("valueOption", TypeString, isOption = true)

      memberInfo(stringTest2, "value") must be equalTo FXBeanClassMemberInfo("value", TypeString)
      memberInfo(stringTest2, "valueOption") must be equalTo FXBeanClassMemberInfo("valueOption", TypeString, isOption = true)
      memberInfo(stringTest2, "testMethod") must be equalTo FXBeanClassMemberInfo("testMethod", TypeUnknown, isOption = false)


      memberInfo(longTest, "value") must be equalTo FXBeanClassMemberInfo("value", TypeLong)
      memberInfo(longTest, "valueOption") must be equalTo FXBeanClassMemberInfo("valueOption", TypeLong, isOption = true)
    }


  }
}
