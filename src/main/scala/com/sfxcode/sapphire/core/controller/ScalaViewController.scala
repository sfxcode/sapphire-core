package com.sfxcode.sapphire.core.controller

import com.sfxcode.sapphire.core.fxml.ScalaFxmlLoading

/**
  * Created by tom on 16.12.15.
  */
abstract class ScalaViewController[T] extends ViewController with ScalaFxmlLoading {

  def view:T = fxml.asInstanceOf[T]

}
