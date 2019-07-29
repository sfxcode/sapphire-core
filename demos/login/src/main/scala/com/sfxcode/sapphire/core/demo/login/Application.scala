package com.sfxcode.sapphire.core.demo.login

import com.sfxcode.sapphire.core.application.FXApp
import com.sfxcode.sapphire.core.controller.AppController
import com.sfxcode.sapphire.core.demo.login.controller.{LoginController, ProfileController}
import com.sfxcode.sapphire.core.demo.login.model.User
import com.sfxcode.sapphire.core.value.FXBean
import javafx.stage.Stage
import javax.enterprise.context.ApplicationScoped
import javax.inject.Named

object Application extends FXApp {
  override def stage: Stage = createDefaultStage

}

@Named
@ApplicationScoped
class LoginApplicationController extends AppController {
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
