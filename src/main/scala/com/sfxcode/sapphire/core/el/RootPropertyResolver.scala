package com.sfxcode.sapphire.core.el

import java.beans.FeatureDescriptor
import java.util
import javax.el.{ELContext, ELResolver, PropertyNotFoundException}

import com.sfxcode.sapphire.core.cdi.CDILauncher
import org.apache.deltaspike.core.api.provider.BeanProvider

import scalafx.collections.ObservableMap

class RootPropertyResolver extends ELResolver {
  val map = ObservableMap[String, AnyRef]()

  def setValue(context: ELContext, base: Any, property: Any, value: AnyRef) {
    if (resolve(context, base, property))
      map.put(property.toString, value)
  }

  def getValue(context: ELContext, base: Any, property: Any): AnyRef = {
    if (resolve(context, base, property)) {
      val propertyString: String = property.toString
      if (map.contains(propertyString))
        return map(propertyString)
      else {
        val result = BeanProvider.getContextualReference(propertyString, true)
        if (result == null)
          throw new PropertyNotFoundException("Cannot find property " + property)
        else
          return result
      }
    }
    null
  }

  def getType(context: ELContext, base: Any, property: Any): Class[_] = {
    if (resolve(context, base, property))
      classOf[AnyRef]
    else
      null
  }

  def isReadOnly(context: ELContext, base: Any, property: Any): Boolean = false

  def getFeatureDescriptors(context: ELContext, base: Any): util.Iterator[FeatureDescriptor] = null

  def getCommonPropertyType(context: ELContext, base: Any): Class[_] = {
    if (base == null)
      classOf[String]
    else
      null
  }

  override def invoke(context: ELContext, base: Any, method: Any, paramTypes: Array[Class[_]], params: Array[Object]): AnyRef = {
    if (resolve(context, base, method))
      throw new NullPointerException("Cannot invoke method " + method + " on null")
    null
  }

  def resolve(context: ELContext, base: Any, property: Any): Boolean = {
    context.setPropertyResolved(base == null && property.isInstanceOf[String])
    context.isPropertyResolved
  }
}

object RootPropertyResolver {

  def apply(): RootPropertyResolver = {
    new RootPropertyResolver
  }
}
