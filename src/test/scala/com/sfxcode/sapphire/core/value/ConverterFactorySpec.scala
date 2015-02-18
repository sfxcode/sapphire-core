package com.sfxcode.sapphire.core.value

import org.apache.deltaspike.core.api.exclude.Exclude

import org.specs2.mutable.Specification

import javafx.util.converter._


@Exclude
class ConverterFactorySpec extends Specification {


  "ConverterFactory" should {

    "return Default Converter" in {
      val c = ConverterFactory.getConverterByName("unknown")
      c must haveClass[DefaultStringConverter]

    }

    "return Converter for name" in {

      val bc = ConverterFactory.getConverterByName("BooleanStringConverter")
      bc must haveClass[BooleanStringConverter]

      val ic = ConverterFactory.getConverterByName("IntegerStringConverter")
      ic must haveClass[IntegerStringConverter]
    }
  }
}
