package com.sfxcode.sapphire.data.reflect

import java.lang.reflect.Field
import java.time.LocalDate
import java.util.Date

import com.sfxcode.sapphire.data.reflect.MemberInfo._

import scala.collection.mutable

object PropertyType extends Enumeration {
  type PropertyValue = Value
  val TypeUnknown, TypeString, TypeInt, TypeLong, TypeFloat, TypeDouble, TypeBoolean, TypeLocalDate, TypeDate = Value
}

import com.sfxcode.sapphire.data.reflect.PropertyType._

object FXBeanClassRegistry {

  private val infoMap = new mutable.HashMap[Class[_], mutable.HashMap[String, MemberInfo]]()

  def memberInfo(target: AnyRef, name: String): MemberInfo = {
    val clazz = target.getClass
    if (!infoMap.contains(clazz))
      infoMap.put(clazz, new mutable.HashMap[String, MemberInfo]())
    val memberInfoMap = infoMap(clazz)
    if (memberInfoMap.contains(name))
      return memberInfoMap(name)
    var result = MemberInfo(name)

    val fieldOption = ClassUtil.field(target, name)
    if (fieldOption.isDefined) {
      val field: Field = fieldOption.get

      val isJavaType = field.getClass.getName.contains("java")

      val memberClazz = field.getType
      var optionClass = classOf[String]
      val isOption    = memberClazz.getName.contains("scala.Option")

      if (memberClazz == classOf[Option[String]])
        result = stringInfo(name, isOption = true)
      else if (memberClazz == classOf[String])
        result = stringInfo(name)
      else if (memberClazz == classOf[Option[Int]])
        result = intInfo(name, isOption = true)
      else if (memberClazz == classOf[Int])
        result = intInfo(name)
      else if (memberClazz == classOf[Option[Long]])
        result = longInfo(name, isOption = true)
      else if (memberClazz == classOf[Long])
        result = longInfo(name)
      else if (memberClazz == classOf[Option[Float]])
        result = floatInfo(name, isOption = true)
      else if (memberClazz == classOf[Float])
        result = floatInfo(name)
      else if (memberClazz == classOf[Option[Double]])
        result = doubleInfo(name, isOption = true)
      else if (memberClazz == classOf[Double])
        result = doubleInfo(name)
      else if (memberClazz == classOf[Option[Boolean]])
        result = boolInfo(name, isOption = true)
      else if (memberClazz == classOf[Boolean])
        result = boolInfo(name)
      else if (memberClazz == classOf[Option[LocalDate]])
        result = localDateInfo(name, isOption = true)
      else if (memberClazz == classOf[LocalDate])
        result = localDateInfo(name)
      else if (memberClazz == classOf[Option[Date]])
        result = dateInfo(name, isOption = true)
      else if (memberClazz == classOf[Date])
        result = dateInfo(name)
    }

    memberInfoMap.put(name, result)
    result
  }

}

case class MemberInfo(
    name: String,
    signature: PropertyValue = TypeUnknown,
    isOption: Boolean = false,
    javaClass: Class[_] = classOf[java.lang.String]
)

object MemberInfo {
  val EmptyTypeHints = List[MemberInfo]()

  def stringInfo(name: String, isOption: Boolean = false): MemberInfo =
    MemberInfo(name, TypeString, isOption)

  def intInfo(name: String, isOption: Boolean = false): MemberInfo =
    MemberInfo(name, TypeInt, isOption, classOf[java.lang.Integer])

  def longInfo(name: String, isOption: Boolean = false): MemberInfo =
    MemberInfo(name, TypeLong, isOption, classOf[java.lang.Long])

  def floatInfo(name: String, isOption: Boolean = false): MemberInfo =
    MemberInfo(name, TypeFloat, isOption, classOf[java.lang.Float])

  def doubleInfo(name: String, isOption: Boolean = false): MemberInfo =
    MemberInfo(name, TypeDouble, isOption, classOf[java.lang.Double])

  def boolInfo(name: String, isOption: Boolean = false): MemberInfo =
    MemberInfo(name, TypeBoolean, isOption, classOf[java.lang.Boolean])

  def dateInfo(name: String, isOption: Boolean = false): MemberInfo =
    MemberInfo(name, TypeDate, isOption, classOf[java.util.Date])

  def localDateInfo(name: String, isOption: Boolean = false): MemberInfo =
    MemberInfo(name, TypeLocalDate, isOption, classOf[java.time.LocalDate])

}
