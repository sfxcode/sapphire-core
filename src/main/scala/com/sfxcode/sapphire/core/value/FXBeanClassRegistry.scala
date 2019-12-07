package com.sfxcode.sapphire.core.value

import java.time.LocalDate
import java.util.Date

import com.sfxcode.sapphire.core.value.FXBeanClassMemberInfo._

import scala.collection.mutable
import scala.reflect.runtime.universe._

object PropertyType extends Enumeration {
  type PropertyValue = Value
  val TypeUnknown, TypeString, TypeInt, TypeLong, TypeFloat, TypeDouble, TypeBoolean, TypeLocalDate, TypeDate = Value
}

import com.sfxcode.sapphire.core.value.PropertyType._

object FXBeanClassRegistry {

  private val infoMap = new mutable.HashMap[Class[_], mutable.HashMap[String, FXBeanClassMemberInfo]]()

  def memberInfo(target: Any, name: String): FXBeanClassMemberInfo = {
    val clazz = target.getClass
    if (!infoMap.contains(clazz)) {
      infoMap.put(clazz, new mutable.HashMap[String, FXBeanClassMemberInfo]())
    }
    val memberInfoMap = infoMap(clazz)
    if (memberInfoMap.contains(name))
      return memberInfoMap(name)
    var result = FXBeanClassMemberInfo(name)

    val fieldType = ReflectionTools.getFieldType(target, name)
    if (fieldType.isDefined) {
      val t = fieldType.get

      val isJavaType = t.toString.contains("java")

      if (!isJavaType && fieldType.get =:= typeOf[Option[String]])
        result = stringInfo(name, isOption = true)
      else if (t =:= typeOf[String] || t.toString.contains("String"))
        result = stringInfo(name)
      else if (!isJavaType && fieldType.get =:= typeOf[Option[Int]])
        result = intInfo(name, isOption = true)
      else if (t =:= typeOf[Int] || t.toString.contains("Int"))
        result = intInfo(name)
      else if (!isJavaType && fieldType.get =:= typeOf[Option[Long]])
        result = longInfo(name, isOption = true)
      else if (t =:= typeOf[Long] || t.toString.contains("Long"))
        result = longInfo(name)
      else if (!isJavaType && fieldType.get =:= typeOf[Option[Float]])
        result = floatInfo(name, isOption = true)
      else if (t =:= typeOf[Float] || t.toString.contains("Float"))
        result = floatInfo(name)
      else if (!isJavaType && fieldType.get =:= typeOf[Option[Double]])
        result = doubleInfo(name, isOption = true)
      else if (t =:= typeOf[Double] || t.toString.contains("Double"))
        result = doubleInfo(name)
      else if (!isJavaType && fieldType.get =:= typeOf[Option[Boolean]])
        result = boolInfo(name, isOption = true)
      else if (t =:= typeOf[Double] || t.toString.contains("Boolean"))
        result = boolInfo(name)
      else if (!isJavaType && fieldType.get =:= typeOf[Option[LocalDate]])
        result = localDateInfo(name, isOption = true)
      else if (t =:= typeOf[LocalDate] || t.toString.contains("LocalDate"))
        result = localDateInfo(name)
      else if (!isJavaType && fieldType.get =:= typeOf[Option[LocalDate]])
        result = localDateInfo(name, isOption = true)
      else if (t =:= typeOf[LocalDate] || t.toString.contains("LocalDate"))
        result = localDateInfo(name)
      else if (!isJavaType && fieldType.get =:= typeOf[Option[Date]])
        result = dateInfo(name, isOption = true)
      else if (t =:= typeOf[Date] || t.toString.contains("Date"))
        result = dateInfo(name)
      else if (!isJavaType && fieldType.get =:= typeOf[Option[Date]])
        result = dateInfo(name, isOption = true)
      else if (t =:= typeOf[Date] || t.toString.contains("Date"))
        result = dateInfo(name)
    }

    memberInfoMap.put(name, result)
    result
  }

}

case class FXBeanClassMemberInfo(name: String,
                                 signature: PropertyValue = TypeUnknown,
                                 isOption: Boolean = false,
                                 javaClass: Class[_] = classOf[java.lang.String])

object FXBeanClassMemberInfo {
  val EmptyTypeHints = List[FXBeanClassMemberInfo]()

  def stringInfo(name: String, isOption: Boolean = false): FXBeanClassMemberInfo =
    FXBeanClassMemberInfo(name, TypeString, isOption)

  def intInfo(name: String, isOption: Boolean = false): FXBeanClassMemberInfo =
    FXBeanClassMemberInfo(name, TypeInt, isOption, classOf[java.lang.Integer])

  def longInfo(name: String, isOption: Boolean = false): FXBeanClassMemberInfo =
    FXBeanClassMemberInfo(name, TypeLong, isOption, classOf[java.lang.Long])

  def floatInfo(name: String, isOption: Boolean = false): FXBeanClassMemberInfo =
    FXBeanClassMemberInfo(name, TypeFloat, isOption, classOf[java.lang.Float])

  def doubleInfo(name: String, isOption: Boolean = false): FXBeanClassMemberInfo =
    FXBeanClassMemberInfo(name, TypeDouble, isOption, classOf[java.lang.Double])

  def boolInfo(name: String, isOption: Boolean = false): FXBeanClassMemberInfo =
    FXBeanClassMemberInfo(name, TypeBoolean, isOption, classOf[java.lang.Boolean])

  def dateInfo(name: String, isOption: Boolean = false): FXBeanClassMemberInfo =
    FXBeanClassMemberInfo(name, TypeDate, isOption, classOf[java.util.Date])

  def localDateInfo(name: String, isOption: Boolean = false): FXBeanClassMemberInfo =
    FXBeanClassMemberInfo(name, TypeLocalDate, isOption, classOf[java.time.LocalDate])

}
