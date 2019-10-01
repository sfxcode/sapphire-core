package com.sfxcode.sapphire.core

import java.util.ResourceBundle

import scala.util.Try

case class ResourceBundleHolder(underlying: ResourceBundle) extends AnyVal {

  def message(key: String, params: Any*): String = {
    Try(underlying.getString(key))
      .map { f =>
        format(f, params: _*)
      }
      .getOrElse(s"!!--$key--!!")
  }

  private def format(s: String, params: Any*): String = {
    params.zipWithIndex.foldLeft(s) {
      case (res, (value, index)) => {
        res.replace("{" + index + "}", value.toString)
      }
    }
  }

}
