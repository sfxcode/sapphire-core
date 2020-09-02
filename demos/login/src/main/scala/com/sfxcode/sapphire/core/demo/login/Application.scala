package com.sfxcode.sapphire.core.demo.login

import com.sfxcode.sapphire.core.application.FXApp
import com.sfxcode.sapphire.core.controller.DefaultWindowController
import com.sfxcode.sapphire.core.demo.login.controller.{ LoginController, ProfileController }
import com.sfxcode.sapphire.core.demo.login.model.User
import com.sfxcode.sapphire.core.value.FXBean
import javafx.stage.Stage

object Application extends FXApp {

  override def width: Int = 500

  override def height: Int = 500

  override def title: String = "Login Demo"

  override def initStage(stage: Stage): Unit = {
    super.initStage(stage)
    stage.setResizable(false)
  }

  override val applicationController: DefaultWindowController = new LoginApplicationController
}

class LoginApplicationController extends DefaultWindowController {
  lazy val loginController: LoginController = getController[LoginController]()
  lazy val profileController: ProfileController = getController[ProfileController]()

  var applicationUser: Option[FXBean[User]] = None

  def applicationDidLaunch() {
    println("start " + this)
    println(loginController)
    replaceSceneContent(loginController)
    showLogin()
  }

  def showLogin() {
    replaceSceneContent(loginController)
  }

  def showMain() {
    replaceSceneContent(profileController)
  }
}
