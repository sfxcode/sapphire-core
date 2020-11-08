package com.sfxcode.sapphire.data

import com.typesafe.config.{ Config, ConfigFactory }
import com.typesafe.scalalogging.LazyLogging

import scala.jdk.CollectionConverters._

trait Configuration extends LazyLogging {

  val config: Config = ConfigFactory.load()

  def configBooleanValue(path: String, defaultReturnValue: Boolean = false): Boolean =
    configValue[Boolean](path, defaultReturnValue, config.getBoolean)

  private def configValue[E <: Any](path: String, defaultReturnValue: E = None, f: String => E): E =
    if (config.hasPath(path)) {
      var result = defaultReturnValue
      try result = f(path)
      catch {
        case e: Exception =>
          logger.error(e.getMessage, e)
      }
      result
    } else {
      logger.warn("config path: %s not exist".format(path))
      defaultReturnValue
    }

  def configStringValue(path: String, defaultReturnValue: String = ""): String =
    configValue[String](path, defaultReturnValue, config.getString)

  def configIntValue(path: String, defaultReturnValue: Int = 0): Int =
    configValue[Int](path, defaultReturnValue, config.getInt)

  def configLongValue(path: String, defaultReturnValue: Long = 0): Long =
    configValue[Long](path, defaultReturnValue, config.getLong)

  def configDoubleValue(path: String, defaultReturnValue: Double = 0.0): Double =
    configValue[Double](path, defaultReturnValue, config.getDouble)

  def configBooleanValues(path: String): List[Boolean] =
    configValues[Boolean](path, config.getBooleanList)

  private def configValues[E <: Any](path: String, f: String => java.util.List[_]): List[E] =
    if (config.hasPath(path)) {
      var result = List[E]()
      try result = f(path).asScala.toList.asInstanceOf[List[E]]
      catch {
        case e: Exception =>
          logger.error(e.getMessage, e)
      }
      result
    } else {
      logger.warn("config path: %s not exist".format(path))
      List()
    }

  def configStringValues(path: String): List[String] =
    configValues[String](path, config.getStringList)

  def configIntValues(path: String): List[Int] =
    configValues[Int](path, config.getIntList)

  def configLongValues(path: String): List[Long] =
    configValues[Long](path, config.getLongList)

  def configDoubleValues(path: String): List[Double] =
    configValues[Double](path, config.getDoubleList)

}
