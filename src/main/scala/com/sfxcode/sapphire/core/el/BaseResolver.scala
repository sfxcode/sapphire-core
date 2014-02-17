package com.sfxcode.sapphire.core.el

import javax.el._
import java.util
import java.beans.FeatureDescriptor

class BaseResolver() extends ELResolver{

  val delegate = new CompositeELResolver
  delegate.add(RootPropertyResolver())
  delegate.add(new ArrayELResolver(false))
  delegate.add(new ListELResolver(false))
  delegate.add(new MapELResolver(false))
  delegate.add(new ResourceBundleELResolver)
  delegate.add(new BeanELResolver(false))

  def setValue(context: ELContext, base: Any, property: Any, value: Any) {
    delegate.setValue(context, base, property, value)
  }

  def getValue(context: ELContext, base: Any, property: Any): AnyRef = {
    delegate.getValue(context, base, property)
  }

  def getType(context: ELContext, base: Any, property: Any): Class[_] = {
    delegate.getType(context, base, property)
  }

  def isReadOnly(context: ELContext, base: Any, property: Any): Boolean = {
    delegate.isReadOnly(context, base, property)
  }

  def getFeatureDescriptors(context: ELContext, base: Any): util.Iterator[FeatureDescriptor] = {
    delegate.getFeatureDescriptors(context, base)
  }

  def getCommonPropertyType(context: ELContext, base: Any): Class[_] = {
    delegate.getCommonPropertyType(context, base)
  }

  override def invoke(context: ELContext, base: scala.Any, method: scala.Any, paramTypes: Array[Class[_]], params: Array[AnyRef]): AnyRef = {
    delegate.invoke(context, base, method, paramTypes, params)
  }
}

object BaseResolver {

  def apply():BaseResolver = new BaseResolver
}

