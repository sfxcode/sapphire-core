package com.sfxcode.sapphire.core.controller

import javafx.scene.{control => jfxsc}
import javafx.util.converter.{BooleanStringConverter, DefaultStringConverter, IntegerStringConverter}

import com.sfxcode.sapphire.core.cdi.provider.ConverterProvider
import com.sfxcode.sapphire.core.cdi.{ApplicationEnvironment, BeanResolver}
import com.sfxcode.sapphire.core.test.TestEnvironment
import org.specs2.mutable.Specification

import scalafx.beans.property.StringProperty
import scalafx.scene.{control => sfxsc}

class ApplicationEnvironmentSpec extends Specification with BeanResolver {
  TestEnvironment.init()

  "ApplicationEnvironment" should {

    "resolve string property from TextField" in {
      val textField = new jfxsc.TextField()
      val env = getBean[ApplicationEnvironment]()
      val conv = getBean[ConverterProvider]()
      var resolved = env.nodePropertyResolver.resolve(textField)
      resolved.get must beAnInstanceOf[StringProperty]
      resolved = env.nodePropertyResolver.resolve(new sfxsc.TextField(textField))
      resolved.get must beAnInstanceOf[StringProperty]
    }

    "resolve string property from Label" in {
      val label = new jfxsc.Label()
      val env = getBean[ApplicationEnvironment]()

      var resolved = env.nodePropertyResolver.resolve(label)
      resolved.get must beAnInstanceOf[StringProperty]
      resolved = env.nodePropertyResolver.resolve(new sfxsc.Label(label))
      resolved.get must beAnInstanceOf[StringProperty]
    }

  }


  "ConverterFactory" should {

    "return Default Converter" in {
      val factory = getBean[ConverterProvider]()
      val c = factory.getConverterByName("unknown")
      c must haveClass[DefaultStringConverter]

    }

    "return Converter for name" in {
      val factory = getBean[ConverterProvider]()

      val bc = factory.getConverterByName("BooleanStringConverter")
      bc must haveClass[BooleanStringConverter]

      val ic = factory.getConverterByName("IntegerStringConverter")
      ic must haveClass[IntegerStringConverter]
    }
  }
}
