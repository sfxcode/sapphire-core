package com.sfxcode.sapphire.core.cdi.provider

import com.typesafe.scalalogging.LazyLogging
import javafx.collections.{FXCollections, ObservableMap}
import javafx.util.StringConverter
import javafx.util.converter.DefaultStringConverter
import javax.enterprise.context.ApplicationScoped
import javax.inject.Named

@Named
@ApplicationScoped
class ConverterProvider extends Serializable with LazyLogging {

  val converterMap: ObservableMap[String, StringConverter[_]] = FXCollections.observableHashMap[String, StringConverter[_]]()

  def getConverterByName[T](name: String, forceNew: Boolean = false): StringConverter[T] = {

    var className = name
    if (!name.contains("."))
      className = "javafx.util.converter." + guessConverterName(name)

    if (!forceNew && converterMap.containsKey(className)) {
      converterMap.get(className).asInstanceOf[StringConverter[T]]
    } else {

      var result = new DefaultStringConverter().asInstanceOf[StringConverter[T]]

      try {
        val converterClass = Class.forName(className)
        val converter = converterClass.getDeclaredConstructor().newInstance()
        if (converter != null) {
          result = converter.asInstanceOf[StringConverter[T]]
        }
      } catch {
        case e: Exception =>
          logger.warn(e.getMessage)
          logger.warn("use default converter for name: " + className)
      }
      converterMap.put(className, result)
      result
    }

  }

  def guessConverterName(className: String): String = {
    if (!className.endsWith("StringConverter"))
      className + "StringConverter"
    else
      className
  }

}
