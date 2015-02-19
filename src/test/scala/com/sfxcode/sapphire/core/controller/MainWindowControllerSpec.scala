package com.sfxcode.sapphire.core.controller

import com.sfxcode.sapphire.core.test.cdi
import org.apache.deltaspike.core.api.exclude.Exclude
import org.specs2.mutable.Specification

@Exclude
class MainWindowControllerSpec extends Specification {

  "mainWindowController" should {

    "be valid" in new cdi {

      application must not beNull

      mainWindowController must not beNull

      3 must be equalTo (3)
    }
  }

}

