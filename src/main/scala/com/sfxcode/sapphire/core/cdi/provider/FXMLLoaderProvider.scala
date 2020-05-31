package com.sfxcode.sapphire.core.cdi.provider

import javafx.fxml.FXMLLoader
import javafx.util.Callback
import javax.enterprise.inject._
import javax.inject.Inject

import scala.language.implicitConversions

class FXMLLoaderProvider {

  implicit def callbackFromFunction[P, R](f: P => R): Callback[P, R] = (param: P) => f(param)

  @Inject var instance: Instance[AnyRef] = _

  @Produces def createLoader: FXMLLoader = {
    val loader: FXMLLoader = new FXMLLoader

    loader.setControllerFactory({ param: Class[_] => selectByClass[AnyRef](instance, param).get })
    loader
  }

  def selectByClass[T](instance: Instance[T], param: Class[_]): Instance[T] =
    instance.select(param.asInstanceOf[Class[T]])

}
