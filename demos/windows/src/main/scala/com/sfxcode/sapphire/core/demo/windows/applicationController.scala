package com.sfxcode.sapphire.core.demo.windows

import com.sfxcode.sapphire.core.controller.{ AdditionalWindowController, BaseApplicationController }
import com.sfxcode.sapphire.core.demo.windows.controller.{ AdditionalViewController, MainViewController }
import javafx.stage.{ Modality, StageStyle }

class ApplicationController extends BaseApplicationController {

  lazy val mainViewController: MainViewController =
    getController[MainViewController]()

  // #SecondWindowControllerVar
  lazy val secondWindowController = new SecondWindowController
  // #SecondWindowControllerVar

  // #ModalWindowControllerVar
  lazy val modalWindowController = new ModalWindowController
  // #ModalWindowControllerVar

  def applicationDidLaunch() {
    logger.info("start " + this)
    replaceSceneContent(mainViewController)
  }

}

// #AdditionalWindowController
abstract class AbstractWindowController extends AdditionalWindowController {

  lazy val viewController: AdditionalViewController =
    getController[AdditionalViewController]()

  override def startup(): Unit = {
    super.startup()
    val stage: Unit = createStage()

    replaceSceneContent(viewController)
  }

  override def width: Int = 200

  override def height: Int = 200

}
// #AdditionalWindowController

// #SecondWindowController
class SecondWindowController extends AbstractWindowController
// #SecondWindowController

// #ModalWindowController

class ModalWindowController extends AbstractWindowController {
  override def modality: Modality = Modality.APPLICATION_MODAL
  override def stageStyle: StageStyle = StageStyle.UTILITY

}
// #ModalWindowController
