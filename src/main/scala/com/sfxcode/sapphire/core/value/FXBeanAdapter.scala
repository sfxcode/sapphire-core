package com.sfxcode.sapphire.core.value

import com.sfxcode.sapphire.core.cdi.provider.ConverterProvider
import com.sfxcode.sapphire.core.controller.ViewController
import com.typesafe.scalalogging.LazyLogging
import javafx.beans.property._
import javafx.beans.{property => jfxbp}
import javafx.collections.{FXCollections, ObservableMap}
import javafx.scene.Node
import javafx.util.StringConverter

import scala.collection.JavaConverters._

class FXBeanAdapter[T <: AnyRef](val viewController: ViewController, var parent: Node = null) extends KeyConverter with LazyLogging {

  val beanProperty = new SimpleObjectProperty[FXBean[T]]()

  val hasBeanProperty = new SimpleBooleanProperty(false)

  hasBeanProperty.bind(beanProperty.isNotNull)

  beanProperty.addListener((_, oldValue, newValue) => updateBean(oldValue, newValue))

  val nodeCache: ObservableMap[String, Option[Node]] = FXCollections.observableHashMap[String, Option[javafx.scene.Node]]()

  val bindingMap: ObservableMap[Property[_], String] = FXCollections.observableHashMap[Property[_ <: Any], String]()

  val boundProperties: ObservableMap[Property[_], Property[_]] = FXCollections.observableHashMap[Property[_], Property[_]]()

  if (parent == null)
    parent = viewController.rootPane

  def converterProvider: ConverterProvider = viewController.converterFactory

  def set(newValue: FXBean[T]): Unit = beanProperty.setValue(newValue)

  def get: FXBean[T] = beanProperty.get

  def unset(): Unit = set(null)

  def hasValue: Boolean = hasBeanProperty.get

  protected def updateBean(oldValue: FXBean[T], newValue: FXBean[T]): Unit = {
    unbindAll()
    bindAll(newValue)
  }

  protected def unbindAll() {
    boundProperties.keySet.asScala.foreach(p => {
      val p1 = p.asInstanceOf[jfxbp.Property[Any]]
      val p2 = boundProperties.get(p).asInstanceOf[jfxbp.Property[Any]]
      p1.unbindBidirectional(p2)
    })
    boundProperties.clear()
  }

  protected def bindAll(bean: FXBean[T]) {
    if (hasValue)
      bindingMap.keySet().asScala.foreach(property => bindBidirectional(bean, property, bindingMap.get(property)))
  }

  def addBinding(property: Property[_], beanKey: String, converter: Option[StringConverter[T]] = None) {
    if (converter.isDefined && property.isInstanceOf[StringProperty])
      converterMap.put(property.asInstanceOf[StringProperty], converter.get)
    bindingMap.put(property.asInstanceOf[Property[Any]], beanKey)
  }

  def addBindings(keyBindings: KeyBindings) {
    keyBindings.keys.foreach(key => {
      val property = guessPropertyForNode(key)
      if (property.isDefined) {
        bindingMap.put(property.get, keyBindings(key))
      }
    })
  }

  def guessPropertyForNode(key: String): Option[Property[_]] = {
    val node = nodeCache.asScala.getOrElse(key, viewController.locate(key, parent))
    if (node.isDefined) {
      if (!nodeCache.containsKey(key))
        nodeCache.put(key, node)
      val result = viewController.applicationEnvironment.nodePropertyResolver.resolve(node.get)
      logger.debug("resolved property for %s : %s".format(key, result))
      result
    } else {
      logger.warn("can not resolve property for key %s - try to create FXBeanAdapter with parent node".format(key))
      None
    }

  }

  def addDate2Converter(keys: String*): Unit = keys.foreach(addConverter(_, FXBean.defaultDateConverter))

  protected def bindBidirectional[S](bean: FXBean[T], property: Property[S], beanKey: String) {
    val observable = bean.getProperty(beanKey)
    observable match {
      case beanProperty: Property[_] => property match {
        case stringProperty: StringProperty => bindBidirectionalFromStringProperty(stringProperty, observable, beanKey)
        case p: Property[S] => bindBidirectionalFromProperty[S](p, beanProperty.asInstanceOf[Property[S]])
      }
      case _ =>
    }
  }

  protected def bindBidirectionalFromStringProperty[S](stringProperty: StringProperty, beanProperty: Property[S], beanKey: String) {
    val converter = converterMap.asScala.get(stringProperty)
    if (converter.isDefined) {
      val c = converter.get
      val bp = beanProperty.asInstanceOf[Property[S]]
      stringProperty.bindBidirectional[S](bp, c.asInstanceOf[StringConverter[S]])
      boundProperties.put(stringProperty, beanProperty)
    } else
      beanProperty match {
        case _: StringProperty =>
          val defaultStringConverter = viewController.converterFactory.getConverterByName[Any]("DefaultStringConverter")
          stringProperty.bindBidirectional(beanProperty.asInstanceOf[Property[Any]], defaultStringConverter)
          boundProperties.put(stringProperty, beanProperty)
      }
  }

  protected def bindBidirectionalFromProperty[S](nodeProperty: Property[S], beanProperty: Property[S]) {
    nodeProperty.bindBidirectional(beanProperty)
    boundProperties.put(nodeProperty, beanProperty)
  }

  def revert() {
    if (hasValue)
      beanProperty.getValue.revert()
  }

  def clearChanges() {
    if (hasValue)
      beanProperty.getValue.clearChanges()
  }

}

object FXBeanAdapter {

  def apply[T <: AnyRef](viewController: ViewController, parent: Node = null): FXBeanAdapter[T] = {
    new FXBeanAdapter[T](viewController, parent)
  }

}

