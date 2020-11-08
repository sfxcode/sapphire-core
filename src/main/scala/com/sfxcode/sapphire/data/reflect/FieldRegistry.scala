package com.sfxcode.sapphire.data.reflect

import java.lang.reflect.Field

import scala.collection.mutable

object FieldRegistry {
  private val registry = new mutable.HashMap[Class[_], Map[String, Field]]()

  def fieldMap(clazz: Class[_]): Map[String, Field] =
    if (!registry.contains(clazz)) {
      val result = new mutable.HashMap[String, Field]()
      val fields = clazz.getDeclaredFields
      fields.foreach { field =>
        val name = field.getName

        result.+=(name -> field)
      }
      val map = result.toMap
      registry.+=(clazz -> map)
      map
    } else
      registry(clazz)

  def field(target: AnyRef, name: String): Option[Field] =
    if (target != null)
      fieldMap(target.getClass).get(name)
    else
      None

  def memberMap(v: AnyRef): Map[String, Any] = {
    val result = new mutable.HashMap[String, Any]()
    val clazz = v.getClass
    val map = fieldMap(clazz)

    map.keys.foreach { name =>
      val field: Field = map(name)
      field.setAccessible(true)
      val value = field.get(v)
      result.+=(name -> value)
    }
    result.toMap
  }
}
