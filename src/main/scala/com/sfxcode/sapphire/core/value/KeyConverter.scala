package com.sfxcode.sapphire.core.value

import com.sfxcode.sapphire.core.cdi.provider.ConverterProvider
import com.sfxcode.sapphire.core.el.DefaultFunctions
import javafx.beans.property.{Property, StringProperty}
import javafx.collections.{FXCollections, ObservableMap}
import javafx.util.StringConverter
import javafx.util.converter._

abstract class KeyConverter {

  val converterMap: ObservableMap[StringProperty, StringConverter[_]] =
    FXCollections.observableHashMap[StringProperty, StringConverter[_]]()

  def converterProvider: ConverterProvider

  def guessPropertyForNode(key: String): Option[Property[_]]

  def addConverterForKeys(keys: List[String], converterName: String, forceNew: Boolean = false): Unit =
    keys.foreach(key => addConverter(key, converterName, forceNew))

  def addByteConverter(keys: String*): Unit = keys.foreach(addConverter(_, classOf[ByteStringConverter].getSimpleName))

  def addCharConverter(keys: String*): Unit =
    keys.foreach(addConverter(_, classOf[CharacterStringConverter].getSimpleName))

  def addCurrencyConverter(keys: String*): Unit =
    keys.foreach(addConverter(_, classOf[CurrencyStringConverter].getSimpleName))

  def addConverter(key: String, converterName: String, forceNew: Boolean = false) {
    val converter = converterProvider.getConverterByName(converterName, forceNew).asInstanceOf[StringConverter[_]]
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

  def addIntConverter(keys: String*): Unit =
    keys.foreach(addConverter(_, classOf[IntegerStringConverter].getSimpleName))

  def addLongConverter(keys: String*): Unit = keys.foreach(addConverter(_, classOf[LongStringConverter].getSimpleName))

  def addFloatConverter(keys: String*): Unit =
    keys.foreach(addConverter(_, classOf[FloatStringConverter].getSimpleName))

  def addDoubleConverter(keys: String*): Unit =
    keys.foreach(addConverter(_, classOf[DoubleStringConverter].getSimpleName))

  def addShortConverter(keys: String*): Unit =
    keys.foreach(addConverter(_, classOf[ShortStringConverter].getSimpleName))

  def addNumberConverter(keys: String*): Unit =
    keys.foreach(addConverter(_, classOf[NumberStringConverter].getSimpleName))

  def addPercentageConverter(keys: String*): Unit =
    keys.foreach(addConverter(_, classOf[PercentageStringConverter].getSimpleName))

  def addDateConverter(keys: String*): Unit = keys.foreach(addConverter(_, DefaultFunctions.defaultDateConverter))

  def addDateTimeConverter(keys: String*): Unit =
    keys.foreach(addConverter(_, DefaultFunctions.defaultDateTimeConverter))

  def addLocalDateConverter(keys: String*): Unit =
    keys.foreach(addConverter(_, classOf[LocalDateStringConverter].getSimpleName))

  def addLocalDateTimeConverter(keys: String*): Unit =
    keys.foreach(addConverter(_, classOf[LocalDateTimeStringConverter].getSimpleName))

  def addBigDecimalConverter(keys: String*): Unit =
    keys.foreach(addConverter(_, classOf[BigDecimalStringConverter].getSimpleName))

  def addBigIntConverter(keys: String*): Unit =
    keys.foreach(addConverter(_, classOf[BigIntegerStringConverter].getSimpleName))

}
