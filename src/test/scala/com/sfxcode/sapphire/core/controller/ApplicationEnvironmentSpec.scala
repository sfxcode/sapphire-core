package com.sfxcode.sapphire.core.controller

import javafx.scene.{control => jfxsc}

import com.sfxcode.sapphire.core.test.TestEnvironment
import org.specs2.mutable.Specification

import scalafx.beans.property.StringProperty
import scalafx.scene.{control => sfxsc}

class ApplicationEnvironmentSpec extends Specification {
  TestEnvironment.init()

  "ApplicationEnvironment" should {

    "resolve string property from TextField" in {
      val textField = new jfxsc.TextField()
      var resolved = ApplicationEnvironment.nodePropertyResolver.resolve(textField)
      resolved.get must beAnInstanceOf[StringProperty]
      resolved = ApplicationEnvironment.nodePropertyResolver.resolve(new sfxsc.TextField(textField))
      resolved.get must beAnInstanceOf[StringProperty]
    }

    "resolve string property from Label" in {
      val label = new jfxsc.Label()
      var resolved = ApplicationEnvironment.nodePropertyResolver.resolve(label)
      resolved.get must beAnInstanceOf[StringProperty]
      resolved = ApplicationEnvironment.nodePropertyResolver.resolve(new sfxsc.Label(label))
      resolved.get must beAnInstanceOf[StringProperty]
    }


  }
}
