package com.sfxcode.sapphire.core.test



import java.util.concurrent.CountDownLatch
import javafx.application.Application
import javafx.stage.Stage

import com.sfxcode.sapphire.core.cdi.{BeanResolver, CDILauncher}
import com.sfxcode.sapphire.core.demo.appdemo.BaseApplicationController

object TestEnvironment {
  private val latch = new CountDownLatch(1)
  var environmentCreated = false
  var applicationController:BaseApplicationController = null
  def init() {
    if (!environmentCreated) {
      new Thread(new Runnable() {
        def run() {
          Application.launch(classOf[TestEnvironment])
        }
      }).start()
      latch.await()

      environmentCreated = true
    }
  }
}

class TestEnvironment extends Application with BeanResolver{

  override def start(stage: Stage) {
    CDILauncher.init()
    val appController = getBean[BaseApplicationController]()
    appController.mainWindowController
    appController.mainWindowController.didGainVisibilityFirstTime()
    appController.mainWindowController.showWorkspace1()
    appController.mainWindowController.showWorkspace2()
    appController.mainWindowController.showWorkspace3()

    TestEnvironment.applicationController = appController
    TestEnvironment.latch.countDown()
  }
}

