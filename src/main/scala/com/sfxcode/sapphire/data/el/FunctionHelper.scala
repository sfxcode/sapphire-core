package com.sfxcode.sapphire.data.el

import java.lang.reflect.Method

import com.typesafe.scalalogging.LazyLogging
import javafx.collections.{ FXCollections, ObservableMap }
import javax.el.ELProcessor

case class FunctionHelper(processor: ELProcessor) extends LazyLogging {
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
