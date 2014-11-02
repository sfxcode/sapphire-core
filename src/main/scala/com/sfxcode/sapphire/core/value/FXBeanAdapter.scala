package com.sfxcode.sapphire.core.value

import com.typesafe.scalalogging.LazyLogging

import scalafx.beans.property._
import scalafx.scene.Node
import scalafx.scene.control._
import javafx.util.StringConverter
import scalafx.Includes._
import javafx.beans.{property => jfxbp}


import com.sfxcode.sapphire.core.controller.{ApplicationEnvironment, ViewController}

import scalafx.collections.ObservableMap

class FXBeanAdapter[T <: AnyRef](val viewController: ViewController, var parent: Node = null) extends LazyLogging {

  private var fxBean: Option[FXBean[T]] = None

  val nodeCache = ObservableMap[String, Option[javafx.scene.Node]]()

  val converterMap = ObservableMap[StringProperty, StringConverter[_]]()
  val bindingMap = ObservableMap[Property[_, _ <: Any], String]()

  val boundProperties = ObservableMap[Property[_, _ <: Any], Property[_, _ <: Any]]()

  if (parent == null)
    parent = viewController.rootPane

  def set(newBean: Option[FXBean[T]] = None) {
    fxBean.foreach(b => unbindAll())
    fxBean = newBean
    fxBean.foreach(b => bindAll())
  }

  protected def unbindAll() {
    boundProperties.keysIterator.foreach(p => {
      val p1 = p.delegate.asInstanceOf[jfxbp.Property[Any]]
      val p2 = boundProperties(p).delegate.asInstanceOf[jfxbp.Property[Any]]
      p1.unbindBidirectional(p2)
    })
    boundProperties.clear()
  }

  protected def bindAll() {
    bindingMap.keys.foreach(property => bindBidirectional(property, bindingMap(property)))
  }

  protected def bindToNode(node: Node, beanKey: String) {
    node match {
      case label: Label => bindBidirectional(label.textProperty(), beanKey)
      case textField: TextField => bindBidirectional(textField.textProperty(), beanKey)
      case textArea: TextArea => bindBidirectional(textArea.textProperty(), beanKey)
      case checkBox: CheckBox => bindBidirectional(checkBox.selectedProperty(), beanKey)
      case _ =>
    }
  }

  def getBean: Option[T] = {
    if (fxBean.isDefined)
      return Some(fxBean.get.bean)
    None
  }

  def addBinding(property: Property[_, _], beanKey: String, converter: Option[StringConverter[T]] = None) {
    if (converter.isDefined && property.isInstanceOf[StringProperty])
      converterMap.put(property.asInstanceOf[StringProperty], converter.get)
    bindingMap.put(property.asInstanceOf[Property[_, _ <: Any]], beanKey)
  }

  def addBindings(keyBindings: KeyBindings) {
    keyBindings.keys.foreach(key => {
      val property = guessPropertyForNode(key)
      if (property.isDefined) {
        bindingMap.put(property.get, keyBindings(key))
      }
    })
  }

  def guessPropertyForNode(key: String): Option[Property[_, _ <: Any]] = {
    val node = nodeCache.getOrElse(key, viewController.locateInternal(key, parent))
    if (node.isDefined) {
      if (!nodeCache.containsKey(key))
        nodeCache.put(key, node)
      val result = ApplicationEnvironment.nodePropertyResolver.resolve(node.get)
      logger.debug("resolved property for %s : %s".format(key, result))
      result
    }
    else
      None
  }

  def addConverter(key: String, name: String, forceNew: Boolean = false) {
    val converter = ConverterFactory.getConverterByName(name, forceNew).asInstanceOf[StringConverter[_]]
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

  protected def bindBidirectional(property: Property[_, _ <: Any], beanKey: String) {
    val observable = fxBean.get.getProperty(beanKey)
    observable match {
      case beanProperty: Property[Any, Any] => property match {
        case stringProperty: StringProperty => bindBidirectionalFromStringProperty(stringProperty, observable, beanKey)
        case p: Property[Any, Any] => bindBidirectionalFromProperty(p, beanProperty)
      }
      case _ =>
    }
  }

  protected def bindBidirectionalFromStringProperty(stringProperty: StringProperty, beanProperty: Property[_, _ <: Any], beanKey: String) {
    val converter = converterMap.get(stringProperty)
    if (converter.isDefined) {
      val c = converter.get.asInstanceOf[StringConverter[Any]]
      val bp = beanProperty.delegate.asInstanceOf[jfxbp.Property[Any]]
      stringProperty.delegate.bindBidirectional(bp, c)
      boundProperties.put(stringProperty, beanProperty)
    }
    else
      beanProperty match {
        case sp: StringProperty =>
          stringProperty.bindBidirectional(beanProperty.asInstanceOf[Property[Any, Any]], ConverterFactory.getConverterByName[Any]("DefaultStringConverter"))
          boundProperties.put(stringProperty, beanProperty)
      }
  }

  protected def bindBidirectionalFromProperty(nodeProperty: Property[Any, Any], beanProperty: Property[Any, Any]) {
    nodeProperty <==> beanProperty
    boundProperties.put(nodeProperty, beanProperty)
  }

  def revert() {
    fxBean match {
      case b: Some[FXBean[T]] => b.get.revert()
      case _ =>
    }
  }

  def clearChanges() {
    fxBean match {
      case b: Some[FXBean[T]] => b.get.clearChanges()
      case _ =>
    }
  }

}

object FXBeanAdapter {

  def apply[T <: AnyRef](viewController: ViewController, parent: Node = null): FXBeanAdapter[T] = {
    new FXBeanAdapter[T](viewController, parent)
  }

}


