package com.sfxcode.sapphire.data

import com.sfxcode.sapphire.data.el.Expressions
import com.sfxcode.sapphire.data.reflect.FieldMeta._
import com.sfxcode.sapphire.data.reflect.{ FieldMeta, ReflectionTools }
import com.typesafe.scalalogging.LazyLogging
import javafx.beans.value.ObservableValue

import scala.collection.mutable
import scala.jdk.CollectionConverters._

class DataWrapper[T <: AnyRef](val wrappedData: T, typeHints: List[FieldMeta] = EmptyTypeHints)
  extends DataProperties(typeHints)
  with LazyLogging {

  def data: AnyRef = wrappedData

  def apply(key: String): Any =
    getValue(key)

  def getOldValue(key: String): Any =
    if (trackChanges)
      Some(changeManagementMap.get(key)).getOrElse(getValue(key))
    else
      getValue(key)

  def getValue(key: String): Any =
    wrappedData match {
      case map: mutable.Map[String, _] => map(key)
      case map: Map[String, _] => map(key)
      case javaMap: java.util.Map[String, _] => javaMap.get(key)
      case _ => Expressions.evaluateExpressionOnObject(wrappedData, key).get
    }

  def updateValue(key: String, newValue: Any) {
    var valueToUpdate = newValue
    if (newValue == None)
      valueToUpdate = null
    wrappedData match {
      case map: mutable.Map[String, Any] => map.put(key, valueToUpdate)
      case javaMap: java.util.Map[String, Any] => javaMap.put(key, valueToUpdate)
      case _ =>
        if (key.contains(".")) {
          val objectKey = key.substring(0, key.indexOf("."))
          val newKey = key.substring(key.indexOf(".") + 1)
          val value = getValue(objectKey)
          val childBean = createChildForKey(objectKey, value)
          childBean.updateValue(newKey, newValue)
        } else
          ReflectionTools.setFieldValue(wrappedData, key, valueToUpdate)
    }
    val property = propertyMap.asScala.getOrElse(key, getProperty(key))
    updateObservableValue(property, valueToUpdate)
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
      wrappedData match {
        case map: mutable.Map[String, Any] => map.put(key, newValue)
        case javaMap: java.util.Map[String, Any] => javaMap.put(key, newValue)
        case _ => ReflectionTools.setFieldValue(wrappedData, key, newValue)
      }
    }

    expressionMap.keySet.asScala.foreach(k => updateObservableValue(expressionMap.get(k), getValue(k)))

    parentBean.foreach(bean => bean.childHasChanged(observable, oldValue, newValue))
  }

  def childHasChanged(observable: ObservableValue[_], oldValue: Any, newValue: Any): Unit =
    expressionMap.keySet.asScala.foreach(k => updateObservableValue(expressionMap.get(k), getValue(k)))

  def preserveChanges(key: String, oldValue: Any, newValue: Any) {
    if (trackChanges) {
      if (changeManagementMap.containsKey(key)) {
        if (changeManagementMap.get(key) == newValue || newValue.equals(changeManagementMap.get(key)))
          changeManagementMap.remove(key)
      } else
        changeManagementMap.put(key, oldValue)
      hasChangesProperty.setValue(hasManagedChanges)
      if (parentBean.isDefined)
        parentBean.get.hasChangesProperty.setValue(parentBean.get.hasManagedChanges || hasManagedChanges)

    }
  }

  def revert() {
    if (trackChanges) {
      trackChanges = false
      changeManagementMap.asScala.keysIterator.foreach { key =>
        val oldValue = changeManagementMap.get(key)
        updateValue(key, oldValue)
      }
      childrenMap.keySet.foreach(key => childrenMap(key).revert())
      trackChanges = true
      clearChanges()
    }
  }

  override def toString: String =
    "{%s : %s@%s}".format(super.toString, wrappedData, wrappedData.hashCode())

}

object DataWrapper {

  def apply[T <: AnyRef](bean: T, typeHints: List[FieldMeta] = List[FieldMeta]()): DataWrapper[T] =
    new DataWrapper[T](bean, typeHints)
}
