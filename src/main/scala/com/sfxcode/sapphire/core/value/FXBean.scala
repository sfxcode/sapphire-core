package com.sfxcode.sapphire.core.value

import javafx.beans.value.{ObservableValue, ChangeListener}
import javafx.beans.property._
import scala.collection.mutable
import PropertyType._
import com.sfxcode.sapphire.core.el.Expressions
import com.typesafe.config.ConfigFactory
import scalafx.util.converter.DateStringConverter

class FXBean[T <: AnyRef](val bean: T, val typeHints: List[FXBeanClassMemberInfo] = List[FXBeanClassMemberInfo]()) extends ChangeListener[Any] {
  val EmptyMemberInfo = FXBeanClassMemberInfo("name_ignored")
  val memberInfoMap = typeHints.map(info => (info.name, info)).toMap
  var trackChanges = true

  lazy val hasChangesProperty = new SimpleBooleanProperty(bean, "_hasChanges", false)

  lazy val propertyMap = new mutable.HashMap[String, Property[_]]()
  lazy val expressionMap = new mutable.HashMap[String, Property[_]]()

  lazy val changeManagementMap = new mutable.HashMap[String, Any]()

  def apply(key: String): Any = {
    getValue(key)
  }

  def getValue(key: String): Any = {
    bean match {
      case map: mutable.Map[String, _] => map(key)
      case javaMap: java.util.Map[String, _] => javaMap.get(key)
      case _ => Expressions.evaluateExpressionOnObject(bean, key)
    }
  }

  def getOldValue(key: String): Any = {
    if (trackChanges)
      changeManagementMap.get(key).getOrElse(getValue(key))
    else
      getValue(key)
  }

  def updateValue(key: String, newValue: Any) {
    val property = propertyMap.getOrElse(key, getProperty(key))
    bean match {
      case map: mutable.Map[String, Any] => map.put(key, newValue)
      case javaMap: java.util.Map[String, Any] => javaMap.put(key, newValue)
      case _ => ReflectionTools.setMemberValue(bean, key, newValue)
    }
    updateObservableValue(property, newValue)
  }


  def getProperty(key: String): Property[_] = {
    if ("_hasChanges".equals(key))
      return hasChangesProperty
    var value = getValue(key)
    value match {
      case option: Option[_] => value = option.get
      case _ =>
    }
    value match {
      case value1: Property[_] => value1
      case _ =>
        // lookup in local function
       var info = memberInfo(key)
        if (info.signature == TypeUnknown) {
          // lookup in registry
          bean match {
            case map: mutable.Map[String, Any] =>
            case javaMap: java.util.Map[String, Any] =>
            case _ => info =  FXBeanClassRegistry.memberInfo(bean, key)
          }

        }

        if (info.signature != TypeUnknown) {
          if (propertyMap.contains(key))
            propertyMap(key)
          else {
            var result: Any = null
            info.signature match {
              case TypeInt => result = new SimpleIntegerProperty(bean, info.name, value.asInstanceOf[Integer])
              case TypeLong => result = new SimpleLongProperty(bean, info.name, value.asInstanceOf[Long])
              case TypeFloat => result = new SimpleFloatProperty(bean, info.name, value.asInstanceOf[Float])
              case TypeDouble => result = new SimpleDoubleProperty(bean, info.name, value.asInstanceOf[Double])
              case TypeBoolean => result = new SimpleBooleanProperty(bean, info.name, value.asInstanceOf[Boolean])
              case _ => result = new SimpleStringProperty(bean, info.name, value.asInstanceOf[String])
            }

            val property = result.asInstanceOf[Property[_]]
            property.addListener(this)
            propertyMap.put(key, property)
            property
          }
        }
        else {
          if (expressionMap.contains(key))
            expressionMap(key)
          else {
            var result: Any = null

            value match {
              case i: Integer => result = new SimpleIntegerProperty(bean, info.name, i)
              case l: Long => result = new SimpleLongProperty(bean, info.name, l)
              case f: Float => result = new SimpleFloatProperty(bean, info.name, f)
              case d: Double => result = new SimpleDoubleProperty(bean, info.name, d)
              case b: Boolean => result = new SimpleBooleanProperty(bean, info.name, b)
              case s: String => result = new SimpleStringProperty(bean, info.name, s)
              case v: Any => result = createSimpleStringPropertyForObject(v, info.name)
              case _ => result = new SimpleStringProperty(bean, info.name, "")
            }

            val property = result.asInstanceOf[Property[_]]
            expressionMap.put(key, property)
            property
          }
        }
    }
  }

  def memberInfo(name: String): FXBeanClassMemberInfo = memberInfoMap.getOrElse(name, EmptyMemberInfo)

  def createSimpleStringPropertyForObject(value: Any, name: String): SimpleStringProperty = {
    val propertyValue: String = value match {
      case d: java.util.Date => FXBean.defaultDateConverter.toString(d)
      case c: java.util.Calendar => FXBean.defaultDateConverter.toString(c.getTime)
      case c: javax.xml.datatype.XMLGregorianCalendar => FXBean.defaultDateConverter.toString(c.toGregorianCalendar.getTime)
      case v: Any => v.toString
      case _ => ""
    }
    new SimpleStringProperty(bean, name, propertyValue)
  }

  def beanValueChanged(key: String, property: Property[_], oldValue: Any, newValue: Any) {
    updateObservableValue(property, newValue)
  }

  def changed(observable: ObservableValue[_], oldValue: Any, newValue: Any) {
    var key = ""
    propertyMap.keySet.takeWhile(_ => key.length == 0).foreach(k => {
      if (propertyMap(k) == observable)
        key = k
    })
    if (key.length > 0) {
      preserveChanges(key, oldValue, newValue)
      bean match {
        case map: mutable.Map[String, Any] => map.put(key, newValue)
        case javaMap: java.util.Map[String, Any] => javaMap.put(key, newValue)
        case _ => ReflectionTools.setMemberValue(bean, key, newValue)
      }
    }

    expressionMap.keySet.foreach(k => {
      if (k.contains(key))
        updateObservableValue(expressionMap(k), getValue(k))
    })
  }

  def updateObservableValue(property: Property[_], value: Any) {
    property match {
      case s: SimpleStringProperty => s.set(value.asInstanceOf[String])
      case i: SimpleIntegerProperty => i.set(value.asInstanceOf[Integer])
      case l: SimpleLongProperty => l.set(value.asInstanceOf[Long])
      case f: SimpleFloatProperty => f.set(value.asInstanceOf[Float])
      case d: SimpleDoubleProperty => d.set(value.asInstanceOf[Double])
      case _ =>
    }
  }

  def preserveChanges(key: String, oldValue: Any, newValue: Any) {
    if (trackChanges) {
      if (changeManagementMap.contains(key)) {
        if (changeManagementMap(key).equals(newValue))
          changeManagementMap.remove(key)
      } else {
        changeManagementMap.put(key, oldValue)
      }
      hasChangesProperty.setValue(changeManagementMap.size > 0)
    }
  }

  def hasChanges = hasChangesProperty.get()


  def revert() {
    if (trackChanges) {
      trackChanges = false
      changeManagementMap.keysIterator.foreach(key => {
        val oldValue = changeManagementMap(key)
        updateValue(key, oldValue)
      })
      clearChanges()
      trackChanges = true
    }
    // Todo
  }

  def clearChanges() {
    if (trackChanges) {
      changeManagementMap.clear()
      hasChangesProperty.setValue(changeManagementMap.size > 0)
    }
  }

  override def toString: String = {
    "{%s : %s@%s}".format(super.toString, bean, bean.hashCode())
  }
}

object FXBean {
  var defaultDateConverter = new DateStringConverter(ConfigFactory.load().getString("sapphire.core.value.defaultDateConverterPattern"))

  def apply[T <: AnyRef](bean: T, typeHints: List[FXBeanClassMemberInfo] = List[FXBeanClassMemberInfo]()): FXBean[T] = {
    new FXBean[T](bean, typeHints)
  }
}

