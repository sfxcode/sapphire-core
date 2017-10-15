package com.sfxcode.sapphire.core.demo.appdemo.controller

import javax.enterprise.context.ApplicationScoped
import javax.inject.Named

import com.sfxcode.sapphire.core.controller.ViewController

import scalafx.scene.Group
import scalafx.scene.control.Button
import scalafx.scene.layout.AnchorPane

@Named
@ApplicationScoped
class StatusBarController extends ViewController {

  val testContent = new Group {

    children = List(
      new Button {
        text = "State"
        id = "state"
      })
  }

  rootPane = new AnchorPane {
    children.addAll(testContent)
  }

}
