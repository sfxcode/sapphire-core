package com.sfxcode.sapphire.core.demo.base.controller

import com.sfxcode.sapphire.core.controller.ViewController
import scalafx.scene.Group
import scalafx.scene.control.Button
import scalafx.Includes._
import scalafx.scene.layout.AnchorPane


class StatusBarController extends ViewController{

  val testContent = new Group {

     children = List(
       new Button {
         text = "State"
         id = "state"
       })
   }

   rootPane  = new AnchorPane {
     children.addAll(testContent)
   }


 }
