package com.sfxcode.sapphire.core.showcase.code

import java.util
import java.util.regex.{ Matcher, Pattern }

import org.fxmisc.richtext.model.{ StyleSpans, StyleSpansBuilder }

import scala.collection.JavaConverters._

object ScalaHighlighter {

  def listFromString(value: String): List[String] = List(value)

  def computeHighlighting(text: String): StyleSpans[util.Collection[String]] = {
    val matcher: Matcher = PATTERN.matcher(text)
    var lastKwEnd: Int = 0
    val spansBuilder: StyleSpansBuilder[util.Collection[String]] = new StyleSpansBuilder
    while (matcher.find) {

      val styleClass: String = {
        if (matcher.group("KEYWORD") != null)
          "keyword"
        else if (matcher.group("PAREN") != null)
          "paren"
        else if (matcher.group("BRACE") != null)
          "brace"
        else if (matcher.group("BRACKET") != null)
          "bracket"
        else if (matcher.group("SEMICOLON") != null)
          "semicolon"
        else if (matcher.group("STRING") != null)
          "string"
        else if (matcher.group("COMMENT") != null)
          "comment"
        else null

      }

      assert(styleClass != null)

      spansBuilder.add(Array[String]().toList.asJava, matcher.start - lastKwEnd)
      spansBuilder.add(listFromString(styleClass).asJava, matcher.end - matcher.start)
      lastKwEnd = matcher.end
    }
    spansBuilder.add(Array[String]().toList.asJava, text.length - lastKwEnd)

    spansBuilder.create()
  }

  private val KEYWORDS: Array[String] =
    Array[String](
      ":",
      "@FXML",
      "#",
      "<-",
      "←",
      "<:",
      "<%",
      "=",
      "=>",
      "⇒",
      ">:",
      "abstract",
      "case",
      "catch",
      "class",
      "def",
      "do",
      "else",
      "extends",
      "false",
      "final",
      "finally",
      "for",
      "forSome",
      "if",
      "implicit",
      "import",
      "lazy",
      "match",
      "new",
      "null",
      "object",
      "override",
      "package",
      "private",
      "protected",
      "return",
      "sealed",
      "super",
      "this",
      "throw",
      "trait",
      "true",
      "try",
      "type",
      "val",
      "var",
      "while",
      "with",
      "yield")
  private val KEYWORD_PATTERN: String =
    "\\b(" + KEYWORDS.mkString("|") + ")\\b"
  private val PAREN_PATTERN: String =
    "\\(|\\)"
  private val BRACE_PATTERN: String =
    "\\{|\\}"
  private val BRACKET_PATTERN: String =
    "\\[|\\]"
  private val SEMICOLON_PATTERN: String =
    "\\;"
  private val STRING_PATTERN: String =
    "\"([^\"\\\\]|\\\\.)*\""
  private val COMMENT_PATTERN: String =
    "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/"
  private val PATTERN: Pattern =
    Pattern.compile(
      "(?<KEYWORD>" + KEYWORD_PATTERN + ")" + "|(?<PAREN>" + PAREN_PATTERN + ")" + "|(?<BRACE>" + BRACE_PATTERN + ")" + "|(?<BRACKET>" + BRACKET_PATTERN + ")" + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")" + "|(?<STRING>" + STRING_PATTERN + ")" + "|(?<COMMENT>" + COMMENT_PATTERN + ")")

}
