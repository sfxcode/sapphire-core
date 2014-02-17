package com.sfxcode.sapphire.core.value

import scala.collection.mutable
import scala.reflect.runtime.universe._
import scala.Option

object PropertyType extends Enumeration {
  type PropertyValue = Value
  val TypeUnknown, TypeString, TypeInt, TypeLong, TypeFloat, TypeDouble, TypeBoolean = Value
}

import PropertyType._

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
        result = FXBeanClassMemberInfo(name, TypeString, isOption = true)
      else if (t =:= typeOf[String] || t.toString.contains("String"))
        result = FXBeanClassMemberInfo(name, TypeString)
      else if (!isJavaType && fieldType.get =:= typeOf[Option[Int]])
        result = FXBeanClassMemberInfo(name, TypeInt, isOption = true)
      else if (t =:= typeOf[Int] || t.toString.contains("Int"))
      result = FXBeanClassMemberInfo(name, TypeInt)
      else if (!isJavaType && fieldType.get =:= typeOf[Option[Long]])
        result = FXBeanClassMemberInfo(name, TypeLong, isOption = true)
      else if (t =:= typeOf[Long] || t.toString.contains("Long"))
      result = FXBeanClassMemberInfo(name, TypeLong)
      else if (!isJavaType && fieldType.get =:= typeOf[Option[Float]])
        result = FXBeanClassMemberInfo(name, TypeFloat, isOption = true)
      else if (t =:= typeOf[Float] || t.toString.contains("Float"))
      result = FXBeanClassMemberInfo(name, TypeFloat)
      else if (!isJavaType && fieldType.get =:= typeOf[Option[Double]])
        result = FXBeanClassMemberInfo(name, TypeDouble, isOption = true)
      else if (t =:= typeOf[Double] || t.toString.contains("Double"))
        result = FXBeanClassMemberInfo(name, TypeDouble)
      else if (!isJavaType && fieldType.get =:= typeOf[Option[Boolean]])
        result = FXBeanClassMemberInfo(name, TypeBoolean, isOption = true)
      else if (t =:= typeOf[Double] || t.toString.contains("Boolean"))
        result = FXBeanClassMemberInfo(name, TypeBoolean)
    }

    memberInfoMap.put(name, result)
    result
  }



}

case class FXBeanClassMemberInfo(name: String, signature: PropertyValue = TypeUnknown, isOption: Boolean = false)

object FXBeanClassMemberInfo {

  def stringInfo(name: String):FXBeanClassMemberInfo = FXBeanClassMemberInfo(name, TypeString)
  def intInfo(name: String):FXBeanClassMemberInfo = FXBeanClassMemberInfo(name, TypeInt)
  def longInfo(name: String):FXBeanClassMemberInfo = FXBeanClassMemberInfo(name, TypeLong)
  def floatInfo(name: String):FXBeanClassMemberInfo = FXBeanClassMemberInfo(name, TypeFloat)
  def doubleInfo(name: String):FXBeanClassMemberInfo = FXBeanClassMemberInfo(name, TypeDouble)
  def boolInfo(name: String):FXBeanClassMemberInfo = FXBeanClassMemberInfo(name, TypeBoolean)


}
