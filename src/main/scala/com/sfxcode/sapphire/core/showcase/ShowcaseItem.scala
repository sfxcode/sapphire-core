package com.sfxcode.sapphire.core.showcase

import java.net.URL

import com.sfxcode.sapphire.core.controller.ViewController

case class ShowcaseItem(group: String, name: String, callback: () => ViewController) {
  lazy val controller: ViewController = callback()

  def fxmlPath: URL = controller.location.get

  def sourcePath: URL = {
    val path = "/code/%s.txt".format(controller.getClass.getSimpleName)
    getClass.getResource(path)
  }

  def documentationPath: URL = {
    val path = "/markdown/%s.md".format(controller.getClass.getSimpleName)
    getClass.getResource(path)
  }

}
