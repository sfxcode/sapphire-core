package com.sfxcode.sapphire.core.demo.login.controller

import com.sfxcode.sapphire.core.controller.ViewController
import com.sfxcode.sapphire.core.demo.login.LoginApplicationController
import com.sfxcode.sapphire.core.demo.login.model.User
import com.sfxcode.sapphire.core.value._
import javafx.event.ActionEvent
import javafx.scene.control.CheckBox

class ProfileController extends ViewController {

  lazy val userAdapter: FXBeanAdapter[User] = FXBeanAdapter[User](this)

  override def didGainVisibility() {
    super.didGainVisibility()
    val bindings = KeyBindings("email", "phone", "address", "subscribed")
    bindings.add("user", "User: ${_self.name()} Mailsize: (${_self.email().length()})")
    userAdapter.addBindings(bindings)

    userAdapter.set(applicationController().applicationUser.get)
  }

  def actionLogout(event: ActionEvent) {
    userAdapter.revert()
    userAdapter.unset()
    applicationController().applicationUser = None
    applicationController().showLogin()
  }

  def applicationController(): LoginApplicationController = registeredBean[LoginApplicationController].get

  def actionUpdate(event: ActionEvent) {
    debugUserData()
  }

  def debugUserData() {

    val maybeTextField = locateTextField("user")
    println(maybeTextField)

    val checkBoxOption = locate[CheckBox]("#subscribed")
    checkBoxOption.foreach(cb => println(cb.selectedProperty.get))

    println(applicationController().applicationUser.get.bean)
  }
}
