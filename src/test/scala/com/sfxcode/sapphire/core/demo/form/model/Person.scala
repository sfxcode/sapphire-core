package com.sfxcode.sapphire.core.demo.form.model

case class Person(var name: String = "Tim", var age: Int = 42, var description: Option[String] = Some("desc"), var active:Boolean=true)
