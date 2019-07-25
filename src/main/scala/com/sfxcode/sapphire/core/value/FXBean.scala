package com.sfxcode.sapphire.core.value

import java.lang
import java.time.LocalDate

import com.sfxcode.sapphire.core.base.ConfigValues
import com.sfxcode.sapphire.core.el.Expressions
import com.sfxcode.sapphire.core.el.Expressions._
import com.sfxcode.sapphire.core.value.PropertyType._
import com.typesafe.scalalogging.LazyLogging
import javafx.beans.property._
import javafx.beans.value.{ChangeListener, ObservableValue}
import javafx.collections.{FXCollections, ObservableMap}
import javafx.util.converter.DateStringConverter

import scala.collection.JavaConverters._
import scala.collection.mutable

class FXBean[T <: AnyRef](val bean: T, val typeHints: List[FXBeanClassMemberInfo] = List[FXBeanClassMemberInfo]()) extends ChangeListener[Any] with LazyLogging {

  val childrenMap = new mutable.HashMap[String, FXBean[AnyRef]]
  var parentBean: Option[FXBean[AnyRef]] = None

  val EmptyMemberInfo = FXBeanClassMemberInfo("name_ignored")
  val memberInfoMap: Map[String, FXBeanClassMemberInfo] = typeHints.map(info => (info.name, info)).toMap
  var trackChanges = true

  lazy val hasChangesProperty = new SimpleBooleanProperty(bean, "_hasChanges", false)

  lazy val propertyMap: ObservableMap[String, Property[_]] = FXCollections.emptyObservableMap[String, Property[_]]()
  lazy val expressionMap: ObservableMap[String, Property[_]] = FXCollections.emptyObservableMap[String, Property[_]]()

  lazy val changeManagementMap: ObservableMap[String, Any] = FXCollections.emptyObservableMap[String, Any]()

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
      Some(changeManagementMap.get(key)).getOrElse(getValue(key))
    else
      getValue(key)
  }

  private def createChildForKey(key: String, value: Any): FXBean[AnyRef] = {
    if (!childrenMap.contains(key)) {
      val newBean = FXBean(value.asInstanceOf[AnyRef])
      newBean.parentBean = Some(this.asInstanceOf[FXBean[AnyRef]])
      newBean.trackChanges = trackChanges
      childrenMap.+=(key -> newBean)
    }
    childrenMap(key)
  }

  def updateValue(key: String, newValue: Any) {
    var valueToUpdate = newValue
    if (newValue == None)
      valueToUpdate = null
    val property = propertyMap.asScala.getOrElse(key, getProperty(key))
    bean match {
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
          ReflectionTools.setMemberValue(bean, key, valueToUpdate)
    }
    updateObservableValue(property, valueToUpdate)
  }

  def isExpressionKey(key: String): Boolean = {
    key.contains(ExpressionPrefix) || key.contains(FxmlExpressionPrefix)
  }

  // property
  def getStringProperty(key: String): StringProperty = getProperty(key).asInstanceOf[StringProperty]
  def getBooleanProperty(key: String): BooleanProperty = getProperty(key).asInstanceOf[BooleanProperty]
  def getIntegerProperty(key: String): IntegerProperty = getProperty(key).asInstanceOf[IntegerProperty]
  def getLongProperty(key: String): LongProperty = getProperty(key).asInstanceOf[LongProperty]
  def getFloatProperty(key: String): FloatProperty = getProperty(key).asInstanceOf[FloatProperty]
  def getDoubleProperty(key: String): DoubleProperty = getProperty(key).asInstanceOf[DoubleProperty]
  def getObjectProperty[S <: Any](key: String): ObjectProperty[S] = getProperty(key).asInstanceOf[ObjectProperty[S]]

  def getProperty(key: String): Property[_] = {
    if (key.contains(".") && !isExpressionKey(key)) {
      val objectKey = key.substring(0, key.indexOf("."))
      val newKey = key.substring(key.indexOf(".") + 1)
      val value = getValue(objectKey)
      val childBean = createChildForKey(objectKey, value)
      childBean.getProperty(newKey)
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
        case value1: Property[_] => value1
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
            if (propertyMap.containsKey(key))
              propertyMap.get(key)
            else {
              var result: Any = null
              info.signature match {
                case TypeInt => result = new SimpleIntegerProperty(bean, info.name, value.asInstanceOf[Integer])
                case TypeLong => result = new SimpleLongProperty(bean, info.name, value.asInstanceOf[Long])
                case TypeFloat => result = new SimpleFloatProperty(bean, info.name, value.asInstanceOf[Float])
                case TypeDouble => result = new SimpleDoubleProperty(bean, info.name, value.asInstanceOf[Double])
                case TypeBoolean => result = new SimpleBooleanProperty(bean, info.name, value.asInstanceOf[Boolean])
                case TypeLocalDate => result = new SimpleObjectProperty(bean, info.name, value.asInstanceOf[LocalDate])
                case _ => result = createSimpleStringPropertyForObject(value, info.name)
              }

              val property = result.asInstanceOf[Property[_]]
              property.addListener(this)
              propertyMap.put(key, property)
              property
            }
          } else {
            if (expressionMap.containsKey(key))
              expressionMap.get(key)
            else {
              var result: Any = null

              value match {
                case i: Integer => result = new SimpleIntegerProperty(bean, info.name, i)
                case l: Long => result = new SimpleLongProperty(bean, info.name, l)
                case f: Float => result = new SimpleFloatProperty(bean, info.name, f)
                case d: Double => result = new SimpleDoubleProperty(bean, info.name, d)
                case b: Boolean => result = new SimpleBooleanProperty(bean, info.name, b)
                case ld: LocalDate => result = new SimpleObjectProperty(bean, info.name, ld)
                case _ => result = createSimpleStringPropertyForObject(value, info.name)
              }

              val property = result.asInstanceOf[Property[_]]
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
    new SimpleStringProperty(bean, name, propertyValue)
  }

  def beanValueChanged(key: String, property: Property[_], oldValue: Any, newValue: Any) {
    updateObservableValue(property, newValue)
  }

  def changed(observable: ObservableValue[_], oldValue: Any, newValue: Any) {
    var key = ""
    propertyMap.keySet.asScala.takeWhile(_ => key.length == 0).foreach(k => {
      if (propertyMap.get(k) == observable)
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

    expressionMap.keySet.asScala.foreach(k => updateObservableValue(expressionMap.get(k), getValue(k)))

    parentBean.foreach(bean => {
      bean.childHasChanged(observable, oldValue, newValue)
    })
  }

  def childHasChanged(observable: ObservableValue[_], oldValue: Any, newValue: Any): Unit = {
    expressionMap.keySet.asScala.foreach(k => updateObservableValue(expressionMap.get(k), getValue(k)))
  }

  def updateObservableValue(property: Property[_], value: Any) {
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

  def hasManagedChanges: lang.Boolean = {
    val childrenChangeCount: Int = childrenMap.values.map(bean => bean.changeManagementMap.size).sum
    (changeManagementMap.size + childrenChangeCount) > 0
  }

  def preserveChanges(key: String, oldValue: Any, newValue: Any) {
    if (trackChanges) {
      if (changeManagementMap.containsKey(key)) {
        if (changeManagementMap.get(key) == newValue || newValue.equals(changeManagementMap.get(key)))
          changeManagementMap.remove(key)
      } else {
        changeManagementMap.put(key, oldValue)
      }
      hasChangesProperty.setValue(hasManagedChanges)
      if (parentBean.isDefined) {
        parentBean.get.hasChangesProperty.setValue(parentBean.get.hasManagedChanges || hasManagedChanges)
      }

    }
  }

  def hasChanges: Boolean = hasChangesProperty.get()

  def revert() {
    if (trackChanges) {
      trackChanges = false
      changeManagementMap.asScala.keysIterator.foreach(key => {
        val oldValue = changeManagementMap.get(key)
        updateValue(key, oldValue)
      })
      childrenMap.keySet.foreach(key => {
        childrenMap(key).revert()
      })
      trackChanges = true
      clearChanges()
    }
  }

  def clearChanges() {
    if (trackChanges) {
      changeManagementMap.clear()
      hasChangesProperty.setValue(hasManagedChanges)
      childrenMap.values.foreach(_.clearChanges())
    }
  }

  override def toString: String = {
    "{%s : %s@%s}".format(super.toString, bean, bean.hashCode())
  }
}

object FXBean extends ConfigValues {
  var defaultDateConverter = new DateStringConverter(configStringValue("sapphire.core.value.defaultDateConverterPattern"))

  def apply[T <: AnyRef](bean: T, typeHints: List[FXBeanClassMemberInfo] = List[FXBeanClassMemberInfo]()): FXBean[T] = {
    new FXBean[T](bean, typeHints)
  }
}

