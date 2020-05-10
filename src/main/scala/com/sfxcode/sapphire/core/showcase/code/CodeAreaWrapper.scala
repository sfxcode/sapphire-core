package com.sfxcode.sapphire.core.showcase.code

import com.sfxcode.sapphire.core.showcase.code.CodeAreaWrapper._
import org.fxmisc.richtext.{ CodeArea, LineNumberFactory }

case class CodeAreaWrapper(codeArea: CodeArea, highlighter: String = "") {
  codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea))
  codeArea.setWrapText(true)
  codeArea
    .textProperty()
    .addListener { (_, _, newValue) =>
      if (!text.isEmpty) {
        if (HighlightScala.equals(highlighter))
          codeArea.setStyleSpans(0, ScalaHighlighter.computeHighlighting(newValue))
        else if (HighlightXML.equals(highlighter)) {
          codeArea.setStyleSpans(0, XmlHighlighter.computeHighlighting(newValue))

          codeArea.setOnKeyPressed { event =>
            if (event.isControlDown) {

              event.consume()
            }
          }
        } else {
          // do nothing
        }

      }
    }

  def text: String = codeArea.getText

  def replaceText(newText: String): Unit = {
    codeArea.clear()
    codeArea.insertText(0, newText)
  }

}

object CodeAreaWrapper {

  val HighlightScala = "Scala"
  val HighlightXML = "XML"
}
