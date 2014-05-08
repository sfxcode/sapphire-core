package com.sfxcode.sapphire.core.controller

import javafx.fxml.Initializable
import java.net.URL
import java.util.ResourceBundle

abstract class FXMLController extends ViewController with Initializable {

  override def initialize(loc: URL, res: ResourceBundle): Unit = {
    location = loc
    resources = res
  }
}
