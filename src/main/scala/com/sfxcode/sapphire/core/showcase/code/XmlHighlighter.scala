package com.sfxcode.sapphire.core.showcase.code

import java.util.regex.{ Matcher, Pattern }

import org.fxmisc.richtext.model.{ StyleSpans, StyleSpansBuilder }

import scala.collection.JavaConverters._

object XmlHighlighter {
  private val XML_TAG: Pattern =
    Pattern.compile("(?<ELEMENT>(</?\\h*)(\\w+)([^<>]*)(\\h*/?>))" + "|(?<COMMENT><!--[^<>]+-->)")
  private val ATTRIBUTES: Pattern = Pattern.compile("(\\w+\\h*)(=)(\\h*\"[^\"]+\")")

  private val GROUP_OPEN_BRACKET: Int = 2
  private val GROUP_ELEMENT_NAME: Int = 3
  private val GROUP_ATTRIBUTES_SECTION: Int = 4
  private val GROUP_CLOSE_BRACKET: Int = 5
  private val GROUP_ATTRIBUTE_NAME: Int = 1
  private val GROUP_EQUAL_SYMBOL: Int = 2
  private val GROUP_ATTRIBUTE_VALUE: Int = 3

  val EmptyList = List[String]().asJava

  def computeHighlighting(text: String): StyleSpans[java.util.Collection[String]] = {
    val matcher: Matcher = XML_TAG.matcher(text)
    var lastKwEnd: Int = 0
    val spansBuilder: StyleSpansBuilder[java.util.Collection[String]] =
      new StyleSpansBuilder[java.util.Collection[String]]()
    while (matcher.find) {
      spansBuilder.add(EmptyList, matcher.start - lastKwEnd)
      if (matcher.group("COMMENT") != null) {
        spansBuilder.add(listFromString("comment"), matcher.end - matcher.start)
      } else {
        if (matcher.group("ELEMENT") != null) {
          val attributesText: String = matcher.group(GROUP_ATTRIBUTES_SECTION)
          spansBuilder.add(
            listFromString("tagmark"),
            matcher.end(GROUP_OPEN_BRACKET) - matcher.start(GROUP_OPEN_BRACKET))
          spansBuilder.add(listFromString("anytag"), matcher.end(GROUP_ELEMENT_NAME) - matcher.end(GROUP_OPEN_BRACKET))
          if (!attributesText.isEmpty) {
            lastKwEnd = 0
            val amatcher: Matcher = ATTRIBUTES.matcher(attributesText)
            while (amatcher.find) {
              spansBuilder.add(EmptyList, amatcher.start - lastKwEnd)
              spansBuilder.add(
                listFromString("attribute"),
                amatcher.end(GROUP_ATTRIBUTE_NAME) - amatcher.start(GROUP_ATTRIBUTE_NAME))
              spansBuilder.add(
                listFromString("tagmark"),
                amatcher.end(GROUP_EQUAL_SYMBOL) - amatcher.end(GROUP_ATTRIBUTE_NAME))
              spansBuilder.add(
                listFromString("value"),
                amatcher.end(GROUP_ATTRIBUTE_VALUE) - amatcher.end(GROUP_EQUAL_SYMBOL))
              lastKwEnd = amatcher.end
            }
            if (attributesText.length > lastKwEnd) spansBuilder.add(EmptyList, attributesText.length - lastKwEnd)
          }
          lastKwEnd = matcher.end(GROUP_ATTRIBUTES_SECTION)
          spansBuilder.add(listFromString("tagmark"), matcher.end(GROUP_CLOSE_BRACKET) - lastKwEnd)
        }
      }
      lastKwEnd = matcher.end
    }
    spansBuilder.add(EmptyList, text.length - lastKwEnd)
    spansBuilder.create
  }

  def listFromString(value: String): java.util.Collection[String] =
    List(value).asJava

}
