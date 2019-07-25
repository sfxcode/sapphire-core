package com.sfxcode.sapphire.core.demo.login.controller

import com.sfxcode.sapphire.core.controller.ViewController
import com.sfxcode.sapphire.core.demo.login.LoginApplicationController
import com.sfxcode.sapphire.core.demo.login.model.User
import com.sfxcode.sapphire.core.value._
import javafx.event.ActionEvent
import javafx.scene.control.{CheckBox, TextField}

class ProfileController extends ViewController {

  lazy val userAdapter: FXBeanAdapter[User] = FXBeanAdapter[User](this)

  override def didGainVisibility() {
    super.didGainVisibility()
    val bindings = KeyBindings("email", "phone", "address", "subscribed")
    bindings.add("user", "User: ${_self.name()} Mailsize: (${_self.email().length()})")
    userAdapter.addBindings(bindings)

    userAdapter.beanProperty.value = applicationController().applicationUser.get
  }

  def actionLogout(event: ActionEvent) {
    userAdapter.revert()
    userAdapter.unset()
    applicationController().applicationUser = None
    applicationController().showLogin()
  }

  def actionUpdate(event: ActionEvent) {
    debugUserData()
  }

  def applicationController(): LoginApplicationController = {
    getBean[LoginApplicationController]()
  }

  def debugUserData() {

    println(locateTextField("user"))

    println(locateSFX[javafx.scene.control.TextField, TextField]("user"))

    val checkBox = locateSFX[javafx.scene.control.CheckBox, CheckBox]("#subscribed")
    checkBox.foreach(cb => println(cb.selected.value))

    println(applicationController().applicationUser.get.bean)
  }
}
