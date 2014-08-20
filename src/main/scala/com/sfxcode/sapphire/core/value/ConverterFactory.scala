package com.sfxcode.sapphire.core.value

import javafx.util.StringConverter
import javafx.util.converter.DefaultStringConverter

import com.typesafe.scalalogging.LazyLogging

import scalafx.collections.ObservableMap

object ConverterFactory extends LazyLogging{
  val converterMap = ObservableMap[String, StringConverter[_]]()

  def getConverterByName[T](name: String, forceNew:Boolean=false): StringConverter[T] = {

    var className = name
    if (!name.contains("."))
      className = "javafx.util.converter." + name

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
      case e: Exception => logger.error(e.getMessage, e)
    }
    converterMap.put(className, result)
    result

  }

  def guessConverterName(className:String):String = {
    println(className)
    var result = "DefaultStringConverter"
    if (className.endsWith("Integer") || className.equals("Int") )
      result = "IntegerStringConverter"
    println(result)

    result
  }

}
