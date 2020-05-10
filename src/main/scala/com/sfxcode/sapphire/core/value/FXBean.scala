package com.sfxcode.sapphire.core.value

import com.sfxcode.sapphire.core.ConfigValues
import com.sfxcode.sapphire.core.el.Expressions
import com.sfxcode.sapphire.core.value.FXBeanClassMemberInfo._
import com.typesafe.scalalogging.LazyLogging
import javafx.beans.property._
import javafx.beans.value.ObservableValue

import scala.collection.JavaConverters._
import scala.collection.mutable

class FXBean[T <: AnyRef](val bean: T, typeHints: List[FXBeanClassMemberInfo] = EmptyTypeHints)
    extends BeanProperties(typeHints)
    with LazyLogging {

  override def getBean: AnyRef = bean

  def apply(key: String): Any =
    getValue(key)

  def getOldValue(key: String): Any =
    if (trackChanges)
      Some(changeManagementMap.get(key)).getOrElse(getValue(key))
    else
      getValue(key)

  def getValue(key: String): Any =
    bean match {
      case map: mutable.Map[String, _]       => map(key)
      case map: Map[String, _]               => map(key)
      case javaMap: java.util.Map[String, _] => javaMap.get(key)
      case _                                 => Expressions.evaluateExpressionOnObject(bean, key).get
    }

  def updateValue(key: String, newValue: Any) {
    var valueToUpdate = newValue
    if (newValue == None)
      valueToUpdate = null
    val property = propertyMap.asScala.getOrElse(key, getProperty(key))
    bean match {
      case map: mutable.Map[String, Any]       => map.put(key, valueToUpdate)
      case javaMap: java.util.Map[String, Any] => javaMap.put(key, valueToUpdate)
      case _ =>
        if (key.contains(".")) {
          val objectKey = key.substring(0, key.indexOf("."))
          val newKey    = key.substring(key.indexOf(".") + 1)
          val value     = getValue(objectKey)
          val childBean = createChildForKey(objectKey, value)
          childBean.updateValue(newKey, newValue)
        }
        else
          ReflectionTools.setMemberValue(bean, key, valueToUpdate)
    }
    updateObservableValue(property, valueToUpdate)
  }

  def beanValueChanged(key: String, property: Property[_], oldValue: Any, newValue: Any) {
    updateObservableValue(property, newValue)
  }

  def changed(observable: ObservableValue[_], oldValue: Any, newValue: Any) {
    var key = ""
    propertyMap.keySet.asScala
      .takeWhile(_ => key.length == 0)
      .foreach { k =>
        if (propertyMap.get(k) == observable)
          key = k
      }
    if (key.length > 0) {
      preserveChanges(key, oldValue, newValue)
      bean match {
        case map: mutable.Map[String, Any]       => map.put(key, newValue)
        case javaMap: java.util.Map[String, Any] => javaMap.put(key, newValue)
        case _                                   => ReflectionTools.setMemberValue(bean, key, newValue)
      }
    }

    expressionMap.keySet.asScala.foreach(k => updateObservableValue(expressionMap.get(k), getValue(k)))

    parentBean.foreach { bean =>
      bean.childHasChanged(observable, oldValue, newValue)
    }
  }

  def childHasChanged(observable: ObservableValue[_], oldValue: Any, newValue: Any): Unit =
    expressionMap.keySet.asScala.foreach(k => updateObservableValue(expressionMap.get(k), getValue(k)))

  def preserveChanges(key: String, oldValue: Any, newValue: Any) {
    if (trackChanges) {
      if (changeManagementMap.containsKey(key)) {
        if (changeManagementMap.get(key) == newValue || newValue.equals(changeManagementMap.get(key)))
          changeManagementMap.remove(key)
      }
      else {
        changeManagementMap.put(key, oldValue)
      }
      hasChangesProperty.setValue(hasManagedChanges)
      if (parentBean.isDefined) {
        parentBean.get.hasChangesProperty.setValue(parentBean.get.hasManagedChanges || hasManagedChanges)
      }

    }
  }

  def revert() {
    if (trackChanges) {
      trackChanges = false
      changeManagementMap.asScala.keysIterator.foreach { key =>
        val oldValue = changeManagementMap.get(key)
        updateValue(key, oldValue)
      }
      childrenMap.keySet.foreach { key =>
        childrenMap(key).revert()
      }
      trackChanges = true
      clearChanges()
    }
  }

  override def toString: String =
    "{%s : %s@%s}".format(super.toString, bean, bean.hashCode())

}

object FXBean extends ConfigValues {

  def apply[T <: AnyRef](bean: T, typeHints: List[FXBeanClassMemberInfo] = List[FXBeanClassMemberInfo]()): FXBean[T] =
    new FXBean[T](bean, typeHints)
}
