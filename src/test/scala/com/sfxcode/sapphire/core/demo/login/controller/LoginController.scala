package com.sfxcode.sapphire.core.demo.login.controller

import com.sfxcode.sapphire.core.controller.ViewController
import javafx.fxml.FXML
import javafx.scene.control._
import javafx.event.ActionEvent
import com.sfxcode.sapphire.core.demo.login.mdel.UserDatabase
import com.sfxcode.sapphire.core.demo.login.LoginApplicationController

import com.sfxcode.sapphire.core.Includes._


class LoginController extends ViewController {

  @FXML
  var email: TextField = _
  @FXML
  var password: PasswordField = _
  @FXML
  var loginButton: Button = _
  @FXML
  var errorMessage: Label = _

  override def didGainVisibility() {
    super.didGainVisibility()
    errorMessage.setText("")
    email.setText("admin@logindemo.com")
  }

  def actionLogin(event: ActionEvent) {
    val user = UserDatabase.find(email.getText, password.getText)
    val authenticated = user.isDefined
    if (authenticated) {
      applicationController().applicationUser = user
      applicationController().showMain()
    }
  }

  def applicationController():LoginApplicationController =   {
    getBean[LoginApplicationController]()
  }

}
