package com.sfxcode.sapphire.core.el

import java.lang.reflect.Method
import java.util.Date

import com.sfxcode.sapphire.core.ConfigValues
import com.sfxcode.sapphire.core.value.FXBean
import com.typesafe.scalalogging.LazyLogging
import javafx.collections.{FXCollections, ObservableMap}
import javax.el.FunctionMapper

class BaseFunctionMapper extends FunctionMapper with LazyLogging {
  val map: ObservableMap[String, Method] = FXCollections.observableHashMap[String, Method]()

  def resolveFunction(prefix: String, localName: String): Method = {
    map.get(key(prefix, localName))
  }

  def addFunction(prefix: String, localName: String, method: Method): Unit = {
    val functionKey = key(prefix, localName)
    if (map.containsKey(functionKey))
      logger.warn("function override for key: %s".format(functionKey))
    map.put(functionKey, method)
  }

  def key(prefix: String, localName: String): String = {
    "%s:%s".format(prefix, localName)
  }

  def addFunction(prefix: String, localName: String, clazz: Class[_], methodName: String, args: Class[_]*): Unit = {
    var method: Method = null
    try {
      method = clazz.getDeclaredMethod(methodName, args.map(_.asInstanceOf[Class[_]]): _*)
    } catch {
      case e: Exception => logger.warn(e.getMessage, e)
    }
    if (method != null)
      addFunction(prefix, localName, method)
  }

}

object BaseFunctionMapper {
  val SapphireFunctionPrefix = "sf"

  def apply(): BaseFunctionMapper = {
    val result = new BaseFunctionMapper
    val clazz: Class[_] = Class.forName("com.sfxcode.sapphire.core.el.DefaultFunctions")
    result.addFunction(SapphireFunctionPrefix, "frameworkName", clazz, "frameworkName")
    result.addFunction(SapphireFunctionPrefix, "frameworkVersion", clazz, "frameworkVersion")
    result.addFunction(SapphireFunctionPrefix, "dateString", clazz, "dateString", classOf[Any])
    result.addFunction(SapphireFunctionPrefix, "now", clazz, "now")
    result.addFunction(SapphireFunctionPrefix, "nowAsString", clazz, "nowAsString")
    result.addFunction(SapphireFunctionPrefix, "boolString", clazz, "boolString", classOf[Boolean], classOf[String], classOf[String])
    result.addFunction(SapphireFunctionPrefix, "configString", clazz, "configString", classOf[String])
    result
  }

}

object DefaultFunctions extends ConfigValues {

  def frameworkName(): String = com.sfxcode.sapphire.core.BuildInfo.name

  def frameworkVersion(): String = com.sfxcode.sapphire.core.BuildInfo.version

  def boolString(value: Boolean, trueValue: String, falseValue: String): String = {
    if (value)
      trueValue
    else
      falseValue
  }

  def dateString(date: Any): String = {
    date match {
      case d: java.util.Date => FXBean.defaultDateConverter.toString(d)
      case c: java.util.Calendar => FXBean.defaultDateConverter.toString(c.getTime)
      case c: javax.xml.datatype.XMLGregorianCalendar => FXBean.defaultDateConverter.toString(c.toGregorianCalendar.getTime)
      case _ => "unknown date format"
    }
  }

  def now: Date = new Date

  def nowAsString: String = dateString(new java.util.Date)

  def configString(path: String): String = configStringValue(path)

}