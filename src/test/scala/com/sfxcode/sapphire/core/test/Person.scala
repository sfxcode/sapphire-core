package com.sfxcode.sapphire.core.test

import java.util.Date
import scala.io.Source
import org.json4s.{DefaultFormats, native}
import native.Serialization._
import java.text.SimpleDateFormat
import com.sfxcode.sapphire.core.value.FXBean
import javafx.collections.ObservableList
import com.sfxcode.sapphire.core.Includes._

case class Person(
  id:Long,
  guid:String,
  isActive:Boolean,
  balance:Double,
  picture:String,
  age:Int,
  name:String,
  gender:String,
  email:String,
  phone:String,
  address:String,
  about:String,
  registered:Date,
  tags:List[String],
  friends:List[Friend],
  greeting:String,
  favoriteFruit:String
)

case class Friend(id:Long, name:String)

object PersonDatabase  {

  implicit val formats = new DefaultFormats {
    override def dateFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
  }

  val personen = read[List[Person]](fromJson("/test_data.json"))

  val friends = personen(0).friends


  def fromJson(name: String): String = {
    val is = getClass.getResourceAsStream(name)
    Source.fromInputStream(is, "UTF-8").getLines().mkString
  }

  def testPerson(id:Int) = FXBean(personen(id))

  def testFriend(id:Int) = FXBean(friends(id))

  def testPersonen = personen.map(item => FXBean[Person](item))

  def testFriends = friends.map(item => FXBean[Friend](item))
}

