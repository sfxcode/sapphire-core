package com.sfxcode.sapphire.core.test

import java.text.SimpleDateFormat
import java.util.Date

import com.sfxcode.sapphire.core.value.FXBean
import org.json4s.DefaultFormats
import org.json4s.native.Serialization._

import scala.io.Source

case class Person(id: Long,
                  guid: String,
                  isActive: Boolean,
                  balance: Double,
                  picture: String,
                  age: Int,
                  name: String,
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

case class Author(var name: String)

case class Book(id: Long, title: String, pages: Int, author: Author)

object PersonDatabase {

  implicit val formats = new DefaultFormats {
    override def dateFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
  }

  val personen: List[Person] = read[List[Person]](fromJson("/test_data.json"))

  val friends: List[Friend] = personen.head.friends
  val scalaBook = Book(1, "Programming In Scala", 852, Author("Martin Odersky"))

  def fromJson(name: String): String = {
    val is = getClass.getResourceAsStream(name)
    Source.fromInputStream(is, "UTF-8").getLines().mkString
  }

  def testPerson(id: Int) = FXBean(personen(id))

  def testFriend(id: Int) = FXBean(friends(id))

  def testPersonen: List[FXBean[Person]] = personen.map(item => FXBean[Person](item))

  def testFriends: List[FXBean[Friend]] = friends.map(item => FXBean[Friend](item))
}
