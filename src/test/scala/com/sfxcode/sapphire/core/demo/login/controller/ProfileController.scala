package com.sfxcode.sapphire.core.demo.login.controller

import com.sfxcode.sapphire.core.controller.ViewController
import javafx.event.ActionEvent
import com.sfxcode.sapphire.core.demo.login.LoginApplicationController
import com.sfxcode.sapphire.core.value._
import com.sfxcode.sapphire.core.demo.login.mdel.User

class ProfileController extends ViewController {

  lazy val userAdapter = FXBeanAdapter[User](this)

  override def didGainVisibility() {
    super.didGainVisibility()
    val bindings = KeyBindings("email", "phone", "address", "subscribed")
    bindings.add("user", "User: ${_self.name()} Mailsize: (${_self.email().length()})")
    userAdapter.addBindings(bindings)

    userAdapter.set(applicationController().applicationUser)
  }

  def actionLogout(event: ActionEvent) {
    userAdapter.set()
    applicationController().applicationUser = None
    applicationController() showLogin()
  }

  def actionUpdate(event: ActionEvent) {
    println(locateTextField("user"))
    println(applicationController().applicationUser.get.bean)
  }

  def applicationController(): LoginApplicationController = {
    getBean[LoginApplicationController]()
  }
}
