package com.sfxcode.sapphire.core.value

import java.lang
import java.time.LocalDate

import com.sfxcode.sapphire.core.el.{ DefaultFunctions, ObjectExpressionHelper }
import com.sfxcode.sapphire.core.value.PropertyType.{
  TypeBoolean,
  TypeDouble,
  TypeFloat,
  TypeInt,
  TypeLocalDate,
  TypeLong,
  _
}
import javafx.beans.property._
import javafx.beans.value.ChangeListener
import javafx.collections.{ FXCollections, ObservableMap }

import scala.collection.mutable

abstract class BeanProperties(val typeHints: List[FXBeanClassMemberInfo]) extends ChangeListener[Any] {
  lazy val changeManagementMap: ObservableMap[String, Any] = FXCollections.observableHashMap[String, Any]()
  lazy val hasChangesProperty = new SimpleBooleanProperty(getBean, "_hasChanges", false)
  lazy val expressionMap: ObservableMap[String, Property[_]] = FXCollections.observableHashMap[String, Property[_]]()
  lazy val propertyMap: ObservableMap[String, Property[_]] = FXCollections.observableHashMap[String, Property[_]]()
  val childrenMap = new mutable.HashMap[String, FXBean[AnyRef]]
  val EmptyMemberInfo = FXBeanClassMemberInfo("name_ignored")
  val memberInfoMap: Map[String, FXBeanClassMemberInfo] = typeHints.map(info => (info.name, info)).toMap
  var parentBean: Option[FXBean[AnyRef]] = None
  var trackChanges = true

  def getBean: AnyRef

  def getValue(key: String): Any

  def hasChanges: Boolean = hasChangesProperty.get()

  def memberInfo(name: String): FXBeanClassMemberInfo =
    memberInfoMap.getOrElse(name, EmptyMemberInfo)

  // property
  def getStringProperty(key: String): StringProperty = getProperty(key).asInstanceOf[StringProperty]

  def getBooleanProperty(key: String): BooleanProperty = getProperty(key).asInstanceOf[BooleanProperty]

  def getIntegerProperty(key: String): IntegerProperty = getProperty(key).asInstanceOf[IntegerProperty]

  def getLongProperty(key: String): LongProperty = getProperty(key).asInstanceOf[LongProperty]

  def getFloatProperty(key: String): FloatProperty = getProperty(key).asInstanceOf[FloatProperty]

  def getDoubleProperty(key: String): DoubleProperty = getProperty(key).asInstanceOf[DoubleProperty]

  def getObjectProperty[S <: Any](key: String): ObjectProperty[S] = getProperty(key).asInstanceOf[ObjectProperty[S]]

  def getProperty(key: String): Property[_] =
    if (key.contains(".") && !ObjectExpressionHelper.isExpressionKey(key)) {
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
            getBean match {
              case map: mutable.Map[String, Any] =>
              case javaMap: java.util.Map[String, Any] =>
              case _ => info = FXBeanClassRegistry.memberInfo(getBean, key)
            }

          }

          if (info.signature != TypeUnknown) {
            if (propertyMap.containsKey(key))
              propertyMap.get(key)
            else {
              var result: Any = null
              info.signature match {
                case TypeInt => result = new SimpleIntegerProperty(getBean, info.name, value.asInstanceOf[Integer])
                case TypeLong => result = new SimpleLongProperty(getBean, info.name, value.asInstanceOf[Long])
                case TypeFloat => result = new SimpleFloatProperty(getBean, info.name, value.asInstanceOf[Float])
                case TypeDouble => result = new SimpleDoubleProperty(getBean, info.name, value.asInstanceOf[Double])
                case TypeBoolean => result = new SimpleBooleanProperty(getBean, info.name, value.asInstanceOf[Boolean])
                case TypeLocalDate =>
                  result = new SimpleObjectProperty(getBean, info.name, value.asInstanceOf[LocalDate])
                case _ => result = createPropertyForObject(value, info.name)
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
                case i: Integer => result = new SimpleIntegerProperty(getBean, info.name, i)
                case l: Long => result = new SimpleLongProperty(getBean, info.name, l)
                case f: Float => result = new SimpleFloatProperty(getBean, info.name, f)
                case d: Double => result = new SimpleDoubleProperty(getBean, info.name, d)
                case b: Boolean => result = new SimpleBooleanProperty(getBean, info.name, b)
                case ld: LocalDate => result = new SimpleObjectProperty(getBean, info.name, ld)
                case _ => result = createPropertyForObject(value, info.name)
              }

              val property = result.asInstanceOf[Property[_]]
              expressionMap.put(key, property)
              property
            }
          }
      }
    }

  def updateObservableValue(property: Property[_], value: Any) {
    property match {
      case property: StringProperty =>
        value match {
          case s: String => property.set(s)
          case d: java.util.Date => property.set(DefaultFunctions.defaultDateConverter.toString(d))
          case c: java.util.Calendar => property.set(DefaultFunctions.defaultDateConverter.toString(c.getTime))
          case c: javax.xml.datatype.XMLGregorianCalendar =>
            property.set(DefaultFunctions.defaultDateConverter.toString(c.toGregorianCalendar.getTime))
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

  def clearChanges() {
    if (trackChanges) {
      changeManagementMap.clear()
      hasChangesProperty.setValue(hasManagedChanges)
      childrenMap.values.foreach(_.clearChanges())
    }
  }

  protected def createPropertyForObject(value: Any, name: String): Any = {
    val propertyValue: Any = value match {
      case d: java.util.Date => DefaultFunctions.defaultDateConverter.toString(d)
      case c: java.util.Calendar => DefaultFunctions.defaultDateConverter.toString(c.getTime)
      case c: javax.xml.datatype.XMLGregorianCalendar =>
        DefaultFunctions.defaultDateConverter.toString(c.toGregorianCalendar.getTime)
      case v: AnyRef => v
      case _ => ""
    }
    propertyValue match {
      case s: String => new SimpleStringProperty(getBean, name, s)
      case v: AnyRef => new SimpleObjectProperty(getBean, name, v)
      case _ => new SimpleStringProperty(getBean, name, "s")
    }
  }

  protected def createChildForKey(key: String, value: Any): FXBean[AnyRef] = {

    if (!childrenMap.contains(key)) {
      val newBean = FXBean(value.asInstanceOf[AnyRef])
      newBean.parentBean = Some(this.asInstanceOf[FXBean[AnyRef]])
      newBean.trackChanges = trackChanges
      childrenMap.+=(key -> newBean)
    }
    childrenMap(key)
  }

}
