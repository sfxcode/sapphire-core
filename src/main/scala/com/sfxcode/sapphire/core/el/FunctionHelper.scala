package com.sfxcode.sapphire.core.el

import java.lang.reflect.Method
import java.util.Date

import com.sfxcode.sapphire.core.application.{ ApplicationEnvironment }
import com.sfxcode.sapphire.core.{ ConfigValues, ResourceBundleHolder }
import com.typesafe.scalalogging.LazyLogging
import javafx.collections.{ FXCollections, ObservableMap }
import javafx.util.converter.{ DateStringConverter, DateTimeStringConverter }
import javax.el.ELProcessor

import scala.annotation.varargs

class FunctionHelper(processor: ELProcessor) extends LazyLogging {
  val map: ObservableMap[String, Method] = FXCollections.observableHashMap[String, Method]()

  def resolveFunction(prefix: String, localName: String): Method =
    map.get(key(prefix, localName))

  def key(prefix: String, localName: String): String =
    "%s:%s".format(prefix, localName)

  def addFunction(prefix: String, localName: String, clazz: Class[_], methodName: String, args: Class[_]*): Unit = {
    var method: Method = null
    try method = clazz.getDeclaredMethod(methodName, args.map(_.asInstanceOf[Class[_]]): _*)
    catch {
      case e: Exception => logger.warn(e.getMessage, e)
    }
    if (method != null)
      addFunction(prefix, localName, method)
  }

  def addFunction(prefix: String, localName: String, method: Method): Unit = {
    val functionKey = key(prefix, localName)
    if (map.containsKey(functionKey))
      logger.warn("function override for key: %s".format(functionKey))
    map.put(functionKey, method)
    processor.defineFunction(prefix, localName, method)

  }

}

object FunctionHelper extends LazyLogging {
  val SapphireFunctionPrefix = "sf"

  def apply(processor: ELProcessor): FunctionHelper = {
    val result = new FunctionHelper(processor)
    val clazz: Class[_] = Class.forName("com.sfxcode.sapphire.core.el.DefaultFunctions")
    result.addFunction(SapphireFunctionPrefix, "frameworkName", clazz, "frameworkName")
    result.addFunction(SapphireFunctionPrefix, "frameworkVersion", clazz, "frameworkVersion")
    result.addFunction(SapphireFunctionPrefix, "dateString", clazz, "dateString", classOf[AnyRef])
    result.addFunction(SapphireFunctionPrefix, "now", clazz, "now")
    result.addFunction(SapphireFunctionPrefix, "nowAsString", clazz, "nowAsString")
    result.addFunction(
      SapphireFunctionPrefix,
      "boolString",
      clazz,
      "boolString",
      classOf[Boolean],
      classOf[String],
      classOf[String])
    result.addFunction(SapphireFunctionPrefix, "configString", clazz, "configString", classOf[String])
    result.addFunction(SapphireFunctionPrefix, "i18n", clazz, "i18n", classOf[String], classOf[Array[Any]])
    result.addFunction(
      SapphireFunctionPrefix,
      "format",
      classOf[java.lang.String],
      "format",
      classOf[String],
      classOf[Array[Any]])
    result
  }

}

object DefaultFunctions extends ConfigValues {
  private lazy val recourceBundleHolder = ResourceBundleHolder(ApplicationEnvironment.resourceBundle)

  val DefaultDateConverterPattern = configStringValue("sapphire.core.value.defaultDateConverterPattern")
  val DefaultDateTimeConverterPattern = configStringValue("sapphire.core.value.defaultDateTimeConverterPattern")

  var defaultDateConverter = new DateStringConverter(DefaultDateConverterPattern)
  var defaultDateTimeConverter = new DateTimeStringConverter(DefaultDateTimeConverterPattern)

  def frameworkName(): String = com.sfxcode.sapphire.core.BuildInfo.name

  def frameworkVersion(): String = com.sfxcode.sapphire.core.BuildInfo.version

  @varargs
  def i18n(key: String, params: Any*): String = recourceBundleHolder.message(key, params: _*)

  def boolString(value: Boolean, trueValue: String, falseValue: String): String =
    if (value)
      trueValue
    else
      falseValue

  def now: Date = new Date

  def nowAsString: String = dateString(new java.util.Date)

  def dateString(date: AnyRef): String = {
    println(date)
    val s = date match {
      case d: java.util.Date => defaultDateConverter.toString(d)
      case c: java.util.Calendar => defaultDateConverter.toString(c.getTime)
      case c: javax.xml.datatype.XMLGregorianCalendar =>
        DefaultFunctions.defaultDateConverter.toString(c.toGregorianCalendar.getTime)
      case _ => "unknown date format"
    }
    s
  }

  def configString(path: String): String = configStringValue(path)

}
