package com.sfxcode.sapphire.core.demo.login

import com.sfxcode.sapphire.core.cdi.FXApp
import com.sfxcode.sapphire.core.controller.AppController
import com.sfxcode.sapphire.core.demo.login.controller.{LoginController, ProfileController}
import com.sfxcode.sapphire.core.demo.login.model.User
import com.sfxcode.sapphire.core.value.FXBean
import javafx.scene.Scene
import javafx.stage.Stage
import javax.enterprise.context.ApplicationScoped
import javax.inject.Named

object Application extends FXApp {

  override def applicationStage: Stage = {
    new PrimaryStage {
      title = "%s Login Demo (%s)".format(BuildInfo.name, BuildInfo.version)
      minWidth = 390
      minHeight = 500
      width = 500
      height = 500
      scene = new Scene {
      }
    }
  }

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
