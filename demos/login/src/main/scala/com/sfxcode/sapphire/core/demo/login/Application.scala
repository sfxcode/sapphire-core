package com.sfxcode.sapphire.core.demo.login

import com.sfxcode.sapphire.core.application.FXApp
import com.sfxcode.sapphire.core.controller.{AppController, DefaultWindowController}
import com.sfxcode.sapphire.core.demo.login.controller.{LoginController, ProfileController}
import com.sfxcode.sapphire.core.demo.login.model.User
import com.sfxcode.sapphire.core.value.FXBean
import javafx.stage.Stage
import javax.enterprise.context.ApplicationScoped
import javax.inject.Named

object Application extends FXApp {

  override def width: Int = 500

  override def height: Int = 500

  override def title: String = "Login Demo"

  override def initStage(stage: Stage): Unit = {
    super.initStage(stage)
    stage.setResizable(false)
  }
}

@Named
@ApplicationScoped
class LoginApplicationController extends DefaultWindowController {
  lazy val loginController = getController[LoginController]()
  lazy val profileController = getController[ProfileController]()

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
