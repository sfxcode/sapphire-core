package com.sfxcode.sapphire.core.value

import com.sfxcode.sapphire.core.controller.ViewController
import javafx.scene.Node
import javafx.scene.control._
import javafx.beans.property._
import scala.collection.mutable
import javafx.util.StringConverter

class FXBeanAdapter[T <: AnyRef](val viewController: ViewController, var parent: Node = null) {

  private var fxBean: Option[FXBean[T]] = None
  private val nodeCache = new mutable.HashMap[String, Option[Node]]()

  private val converterMap = new mutable.HashMap[StringProperty, StringConverter[_]]()
  private val bindingMap = new mutable.HashMap[Property[_], String]()

  private val boundProperties = new mutable.HashMap[Property[_], Property[_]]()

  if (parent == null)
    parent = viewController.rootPane

  def set(newBean: Option[FXBean[T]] = None) {
    fxBean.foreach(b => unbindAll())
    fxBean = newBean
    fxBean.foreach(b => bindAll())
  }

  protected def unbindAll() {
    boundProperties.keysIterator.foreach(p => p.asInstanceOf[Property[Any]].unbindBidirectional(boundProperties(p).asInstanceOf[Property[Any]]))
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
    val node = nodeCache.getOrElse(key, viewController.locateInternal(key, parent))
    node match {
      case option: Some[Node] =>
        if (!nodeCache.get(key).isDefined)
          nodeCache.put(key, option)
        viewController.resolve(node.get)
      case _ => None
    }
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

  protected def bindBidirectional(property: Property[_], beanKey: String) {
    val observable = fxBean.get.getProperty(beanKey)
    observable match {
      case beanProperty: Property[Any] => property match {
        case stringProperty: StringProperty => bindBidirectionalFromStringProperty(stringProperty, observable, beanKey)
        case p: Property[Any] => bindBidirectionalFromProperty(p, beanProperty)
      }
      case _ =>
    }
  }

  protected def bindBidirectionalFromStringProperty(stringProperty: StringProperty, beanProperty: Property[_], beanKey: String) {
    val converter = converterMap.get(stringProperty)
    if (converter.isDefined) {
      stringProperty.bindBidirectional(beanProperty.asInstanceOf[Property[Any]], converter.get.asInstanceOf[StringConverter[Any]])
      boundProperties.put(stringProperty, beanProperty)
    }
    else
      beanProperty match {
        case sp: StringProperty =>
          stringProperty.bindBidirectional(sp)
          boundProperties.put(stringProperty, beanProperty)
      }
  }

  protected def bindBidirectionalFromProperty(nodeProperty: Property[Any], beanProperty: Property[Any]) {
    nodeProperty.bindBidirectional(beanProperty)
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

  def apply[T <:AnyRef](viewController: ViewController, parent: Node = null): FXBeanAdapter[T] = {
    new FXBeanAdapter[T](viewController, parent)
  }

}


