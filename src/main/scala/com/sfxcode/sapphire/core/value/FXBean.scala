package com.sfxcode.sapphire.core.value

import java.time.LocalDate
import javafx.beans.value.{ ChangeListener, ObservableValue }

import com.sfxcode.sapphire.core.el.Expressions
import com.sfxcode.sapphire.core.value.PropertyType._
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging

import scala.collection.mutable
import scalafx.beans.property._
import scalafx.collections.ObservableMap
import scalafx.util.converter.DateStringConverter

class FXBean[T <: AnyRef](val bean: T, val typeHints: List[FXBeanClassMemberInfo] = List[FXBeanClassMemberInfo]()) extends ChangeListener[Any] with LazyLogging {
  val EmptyMemberInfo = FXBeanClassMemberInfo("name_ignored")
  val memberInfoMap: Map[String, FXBeanClassMemberInfo] = typeHints.map(info => (info.name, info)).toMap
  var trackChanges = true

  lazy val hasChangesProperty = new BooleanProperty(bean, "_hasChanges", false)

  lazy val propertyMap: ObservableMap[String, Property[_, _]] = ObservableMap[String, Property[_, _]]()
  lazy val expressionMap: ObservableMap[String, Property[_, _]] = ObservableMap[String, Property[_, _]]()

  lazy val changeManagementMap: ObservableMap[String, Any] = ObservableMap[String, Any]()

  def apply(key: String): Any = {
    getValue(key)
  }

  def getValue(key: String): Any = {
    bean match {
      case map: mutable.Map[String, _] => map(key)
      case map: Map[String, _] => map(key)
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
    var valueToUpdate = newValue
    if (newValue == None)
      valueToUpdate = null
    val property = propertyMap.getOrElse(key, getProperty(key))
    bean match {
      case map: mutable.Map[String, Any] => map.put(key, valueToUpdate)
      case javaMap: java.util.Map[String, Any] => javaMap.put(key, valueToUpdate)
      case _ =>
        if (key.contains(".")) {
          val objectKey = key.substring(0, key.indexOf("."))
          val newKey = key.substring(key.indexOf(".") + 1)
          val value = getValue(objectKey)
          val newBean = FXBean(value.asInstanceOf[AnyRef])
          newBean.updateValue(newKey, newValue)
        } else
          ReflectionTools.setMemberValue(bean, key, valueToUpdate)
    }
    updateObservableValue(property, valueToUpdate)
  }

  def getProperty(key: String): Property[_, _] = {
    if (key.contains(".") && !key.contains(Expressions.ExpressionPrefix)) {
      val objectKey = key.substring(0, key.indexOf("."))
      val newKey = key.substring(key.indexOf(".") + 1)
      val value = getValue(objectKey)
      val newBean = FXBean(value.asInstanceOf[AnyRef])
      newBean.getProperty(newKey)
    } else {
      if ("_hasChanges".equals(key))
        return hasChangesProperty
      var value = getValue(key)
      value match {
        case option: Option[_] =>
          if (option.isDefined)
            value = option.get
          else
            value = null
        case _ =>
      }
      value match {
        case value1: Property[_, _] => value1
        case _ =>
          // lookup in local function
          var info = memberInfo(key)
          if (info.signature == TypeUnknown) {
            // lookup in registry
            bean match {
              case map: mutable.Map[String, Any] =>
              case javaMap: java.util.Map[String, Any] =>
              case _ => info = FXBeanClassRegistry.memberInfo(bean, key)
            }

          }

          if (info.signature != TypeUnknown) {
            if (propertyMap.contains(key))
              propertyMap(key)
            else {
              var result: Any = null
              info.signature match {
                case TypeInt => result = new IntegerProperty(bean, info.name, value.asInstanceOf[Integer])
                case TypeLong => result = new LongProperty(bean, info.name, value.asInstanceOf[Long])
                case TypeFloat => result = new FloatProperty(bean, info.name, value.asInstanceOf[Float])
                case TypeDouble => result = new DoubleProperty(bean, info.name, value.asInstanceOf[Double])
                case TypeBoolean => result = new BooleanProperty(bean, info.name, value.asInstanceOf[Boolean])
                case TypeLocalDate => result = new ObjectProperty(bean, info.name, value.asInstanceOf[LocalDate])
                case _ => result = createSimpleStringPropertyForObject(value, info.name)
              }

              val property = result.asInstanceOf[Property[_, _]]
              property.addListener(this)
              propertyMap.put(key, property)
              property
            }
          } else {
            if (expressionMap.contains(key))
              expressionMap(key)
            else {
              var result: Any = null

              value match {
                case i: Integer => result = new IntegerProperty(bean, info.name, i)
                case l: Long => result = new LongProperty(bean, info.name, l)
                case f: Float => result = new FloatProperty(bean, info.name, f)
                case d: Double => result = new DoubleProperty(bean, info.name, d)
                case b: Boolean => result = new BooleanProperty(bean, info.name, b)
                case ld: LocalDate => result = new ObjectProperty(bean, info.name, ld)
                case _ => result = createSimpleStringPropertyForObject(value, info.name)
              }

              val property = result.asInstanceOf[Property[_, _]]
              expressionMap.put(key, property)
              property
            }
          }
      }
    }
  }

  def memberInfo(name: String): FXBeanClassMemberInfo = memberInfoMap.getOrElse(name, EmptyMemberInfo)

  def createSimpleStringPropertyForObject(value: Any, name: String): StringProperty = {
    val propertyValue: String = value match {
      case d: java.util.Date => FXBean.defaultDateConverter.toString(d)
      case c: java.util.Calendar => FXBean.defaultDateConverter.toString(c.getTime)
      case c: javax.xml.datatype.XMLGregorianCalendar => FXBean.defaultDateConverter.toString(c.toGregorianCalendar.getTime)
      case v: Any => v.toString
      case _ => ""
    }
    new StringProperty(bean, name, propertyValue)
  }

  def beanValueChanged(key: String, property: Property[_, _], oldValue: Any, newValue: Any) {
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

  def updateObservableValue(property: Property[_, _], value: Any) {
    property match {
      case property: StringProperty =>
        value match {
          case s: String => property.set(s)
          case d: java.util.Date => property.set(FXBean.defaultDateConverter.toString(d))
          case c: java.util.Calendar => property.set(FXBean.defaultDateConverter.toString(c.getTime))
          case c: javax.xml.datatype.XMLGregorianCalendar => property.set(FXBean.defaultDateConverter.toString(c.toGregorianCalendar.getTime))
          case _ =>
            if (value != null)
              property.set(value.toString)
            else
              property.set(null)
        }
      case i: IntegerProperty => i.set(value.asInstanceOf[Integer])
      case l: LongProperty => l.set(value.asInstanceOf[Long])
      case f: FloatProperty => f.set(value.asInstanceOf[Float])
      case d: DoubleProperty => d.set(value.asInstanceOf[Double])
      case b: BooleanProperty => b.set(value.asInstanceOf[Boolean])
      case o: ObjectProperty[Any] => o.set(value)
      case _ =>
    }
  }

  def preserveChanges(key: String, oldValue: Any, newValue: Any) {
    if (trackChanges) {
      if (changeManagementMap.contains(key)) {
        if (changeManagementMap(key) == newValue || newValue.equals(changeManagementMap(key)))
          changeManagementMap.remove(key)
      } else {
        changeManagementMap.put(key, oldValue)
      }
      hasChangesProperty.setValue(changeManagementMap.size > 0)
    }
  }

  def hasChanges: Boolean = hasChangesProperty.get()

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

