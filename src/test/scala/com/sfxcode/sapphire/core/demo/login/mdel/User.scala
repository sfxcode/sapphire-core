package com.sfxcode.sapphire.core.demo.login.mdel

import java.util.Date
import com.sfxcode.sapphire.core.value.FXBean

case class User(name:String, var email:String, var password:String, var age:Int=21, var phone:String="", var address:String="",var subscribed:Boolean=false , created:Date=new Date())

object UserDatabase {
  val userList = List(User("admin","admin@logindemo.com", "admin", 42, "555123","Street 42"), User("demo", "demo@logindemo.com", "demo"))

  def find(email:String, password:String):Option[User] = {
    userList.find(u => u.email.equals(email) && u.password.equals(password))
  }
}

case class Login(var name:String, var password:String)
