package com.sfxcode.sapphire.core.showcase.model

import java.text.SimpleDateFormat
import java.util.Date

import com.sfxcode.sapphire.core.value.FXBean
import org.json4s.DefaultFormats
import org.json4s.native.Serialization._

import scala.collection.mutable.ArrayBuffer
import scala.io.Source

case class Person(
  id: Long,
  guid: String,
  isActive: Boolean,
  company: String,
  balance: Double,
  picture: String,
  var age: Int,
  var name: String,
  gender: String,
  email: String,
  phone: String,
  address: String,
  about: String,
  registered: Date,
  tags: List[String],
  friends: List[Friend],
  greeting: String,
  favoriteFruit: String)

case class Friend(id: Long, name: String)

object PersonDatabase {

  implicit val formats = new DefaultFormats {
    override def dateFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
  }

  val persons = read[List[Person]](fromJson("/data.json"))

  val smallPersonTable = {
    persons.take(10)
  }

  val bigPersonTable = {
    var result = ArrayBuffer[Person]()
    (1 to 25).foreach { i =>
      result.++=(persons)
    }
    result.toList
  }

  val friends = persons(2).friends

  def fromJson(name: String): String = {
    val is = getClass.getResourceAsStream(name)
    Source.fromInputStream(is, "UTF-8").getLines().mkString
  }

  def testPerson(id: Int): FXBean[Person] = FXBean(persons(id))

  def testFriend(id: Int): FXBean[Friend] = FXBean(friends(id))

  def smallPersonList = persons.take(10)

}
