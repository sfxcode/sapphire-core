package com.sfxcode.sapphire.core.demo.issues.cdi

import com.sfxcode.sapphire.core.fxml.BaseDocumentLoader
import javafx.fxml.FXMLLoader
import javax.enterprise.context.ApplicationScoped
import javax.inject.{ Inject, Named }

@Named
@ApplicationScoped
class CDIDocumentLoader extends BaseDocumentLoader {

  @Inject
  var fxmlLoader: FXMLLoader = _

  println("sdffds")

}
