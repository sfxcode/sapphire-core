package com.sfxcode.sapphire.core.cdi.provider

import javafx.fxml.FXMLLoader
import javax.enterprise.inject._
import javax.inject.Inject
import scalafx.util.UtilIncludes
import com.sfxcode.sapphire.core.cdi.CdiHelper

class FXMLLoaderProvider extends UtilIncludes {
  @Inject var instance: Instance[AnyRef] = _

  @Produces def createLoader: FXMLLoader = {
    val loader: FXMLLoader = new FXMLLoader

    loader.setControllerFactory({
      param: Class[_] => CdiHelper.selectByClass[AnyRef](instance, param).get
    })
    loader
  }

}
