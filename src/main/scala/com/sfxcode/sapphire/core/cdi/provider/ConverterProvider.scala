package com.sfxcode.sapphire.core.cdi.provider

import javafx.util.StringConverter
import javafx.util.converter.DefaultStringConverter
import javax.enterprise.context.ApplicationScoped
import javax.inject.Named

import com.typesafe.scalalogging.LazyLogging

import scalafx.collections.ObservableMap

@Named
@ApplicationScoped
class ConverterProvider extends Serializable with LazyLogging {
  val converterMap = ObservableMap[String, StringConverter[_]]()

  def getConverterByName[T](name: String, forceNew:Boolean=false): StringConverter[T] = {

    var className = name
    if (!name.contains("."))
      className = "javafx.util.converter." + guessConverterName(name)

    if (!forceNew && converterMap.contains(className))
      return converterMap(className).asInstanceOf[StringConverter[T]]

    var result = new DefaultStringConverter().asInstanceOf[StringConverter[T]]

    try {
      val converterClass = Class.forName(className)
      val converter = converterClass.newInstance()
      if (converter != null) {
        result = converter.asInstanceOf[StringConverter[T]]
      }
    }
    catch {
      case e: Exception =>
        logger.warn("use default converter for name: " + className)
    }
    converterMap.put(className, result)
    result

  }

  def guessConverterName(className:String):String = {
    if (!className.endsWith("StringConverter"))
      return className + "StringConverter"
  className
  }

}
