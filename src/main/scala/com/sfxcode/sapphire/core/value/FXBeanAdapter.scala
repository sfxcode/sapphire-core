package com.sfxcode.sapphire.core.value

import com.sfxcode.sapphire.core.controller.ViewController
import com.typesafe.scalalogging.LazyLogging
import javafx.beans.property._
import javafx.beans.{property => jfxbp}
import javafx.collections.FXCollections
import javafx.scene.Node
import javafx.util.StringConverter

import scala.collection.JavaConverters._

class FXBeanAdapter[T <: AnyRef](val viewController: ViewController, var parent: Node = null) extends LazyLogging {

  val beanProperty = new SimpleObjectProperty[FXBean[T]]()

  val hasBeanProperty = new SimpleBooleanProperty(false)

  hasBeanProperty.bind(beanProperty.isNotNull)

  beanProperty.addListener((_, oldValue, newValue) => updateBean(oldValue, newValue))

  val nodeCache = FXCollections.observableHashMap[String, Option[javafx.scene.Node]]()

  val converterMap = FXCollections.observableHashMap[StringProperty, StringConverter[_]]()
  val bindingMap = FXCollections.observableHashMap[Property[_ <: Any], String]()

  val boundProperties = FXCollections.observableHashMap[Property[_], Property[_]]()

  if (parent == null)
    parent = viewController.rootPane

  def unset(): Unit = beanProperty.setValue(null)

  protected def updateBean(oldValue: FXBean[T], newValue: FXBean[T]) = {
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
    //    if (hasBeanProperty.getValue)
    //      bindingMap.keySet().asScala.foreach(property => bindBidirectional(bean, property, bindingMap.get(property)))
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
    val node = nodeCache.asScala.getOrElse(key, viewController.locateInternal(key, parent))
    if (node.isDefined) {
      if (!nodeCache.containsKey(key))
        nodeCache.put(key, node)
      val result = viewController.applicationEnvironment.nodePropertyResolver.resolve(node.get)
      logger.debug("resolved property for %s : %s".format(key, result))
      result
    } else
      None
  }

  def addConverterForKeys(keys: List[String], converterName: String, forceNew: Boolean = false): Unit = {
    keys.foreach(key => {
      addConverter(key, converterName, forceNew)
    })
  }

  def addConverter(key: String, converterName: String, forceNew: Boolean = false) {
    val converter = viewController.converterFactory.getConverterByName(converterName, forceNew).asInstanceOf[StringConverter[_]]
    addConverter(key, converter)
  }

  def addConverter[S](beanKey: String, converter: StringConverter[S]) {
    val property = guessPropertyForNode(beanKey)
    if (property.isDefined && property.get.isInstanceOf[StringProperty])
      addConverter(property.get.asInstanceOf[StringProperty], converter)
  }

  def addConverter[S](property: StringProperty, converter: StringConverter[S]) {
    converterMap.put(property, converter)
  }

  //  protected def bindBidirectional(bean: FXBean[T], property: Property[_], beanKey: String) {
  //    val observable = bean.getProperty(beanKey)
  //    observable match {
  //      case beanProperty: Property[_] => property match {
  //        case stringProperty: StringProperty => bindBidirectionalFromStringProperty(stringProperty, observable, beanKey)
  //        case p: Property[_] => bindBidirectionalFromProperty(p, beanProperty)
  //      }
  //      case _ =>
  //    }
  //  }

  //  protected def bindBidirectionalFromStringProperty[T](stringProperty: StringProperty, beanProperty: Property[T], beanKey: String) {
  //    val converter = converterMap.asScala.get(stringProperty)
  //    if (converter.isDefined) {
  //      val c = converter.get
  //      val bp = beanProperty.asInstanceOf[jfxbp.Property[T]]
  //      stringProperty.bindBidirectional[T](bp, c)
  //      boundProperties.put(stringProperty, beanProperty)
  //    } else
  //      beanProperty match {
  //        case sp: StringProperty =>
  //          stringProperty.bindBidirectional(beanProperty.asInstanceOf[Property[_]], viewController.converterFactory.getConverterByName[Any]("DefaultStringConverter"))
  //          boundProperties.put(stringProperty, beanProperty)
  //      }
  //  }
  //
  //  protected def bindBidirectionalFromProperty[T <:Any](nodeProperty: Property[T], beanProperty: Property[T]) {
  //    nodeProperty.bindBidirectional(beanProperty)
  //    boundProperties.put(nodeProperty, beanProperty)
  //  }

  def revert() {
    if (hasBeanProperty.getValue)
      beanProperty.getValue.revert()
  }

  def clearChanges() {
    if (hasBeanProperty.getValue)
      beanProperty.getValue.clearChanges()
  }

}

object FXBeanAdapter {

  def apply[T <: AnyRef](viewController: ViewController, parent: Node = null): FXBeanAdapter[T] = {
    new FXBeanAdapter[T](viewController, parent)
  }

}

