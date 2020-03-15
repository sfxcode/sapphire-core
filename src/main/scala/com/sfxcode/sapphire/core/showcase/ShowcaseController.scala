package com.sfxcode.sapphire.core.showcase

import com.jfoenix.controls.{JFXTabPane, JFXTreeView}
import com.sandec.mdfx.MDFXNode
import com.sfxcode.sapphire.core.controller.ViewController
import com.sfxcode.sapphire.core.scene.ContentManager
import com.sfxcode.sapphire.core.showcase.code.CodeAreaWrapper
import javafx.beans.value.{ChangeListener, ObservableValue}
import javafx.fxml.FXML
import javafx.geometry.Insets
import javafx.scene.control.{Label, Tab, TreeItem}
import javafx.scene.layout.{HBox, StackPane}
import org.fxmisc.richtext.CodeArea

import scala.collection.JavaConverters._
import scala.io.Source
import scala.util.Try

abstract class ShowcaseController extends ViewController {

  lazy val jfoenixCss =
    getClass.getResource("/com/sfxcode/sapphire/core/showcase/showcase-jfoenix.css").toExternalForm
  lazy val showcaseCss = getClass.getResource("/com/sfxcode/sapphire/core/showcase/showcase.css").toExternalForm

  lazy val scalaCss =
    getClass.getResource("/com/sfxcode/sapphire/core/showcase/scala-highlighting.css").toExternalForm
  lazy val xmlCss = getClass.getResource("/com/sfxcode/sapphire/core/showcase/xml-highlighting.css").toExternalForm

  lazy val markdownCss = getClass.getResource("/com/sfxcode/sapphire/core/showcase/markdown.css").toExternalForm

  @FXML var infoLabel: Label                       = _
  @FXML var statusLabel: Label                     = _
  @FXML var selectionTreeview: JFXTreeView[String] = _

  @FXML var showcasePane: StackPane = _
  @FXML var showcaseBottomBox: HBox = _

  var showcaseItemManager: ContentManager = _

  @FXML var tabPane: JFXTabPane   = _
  @FXML var showcaseTab: Tab      = _
  @FXML var sourceTab: Tab        = _
  @FXML var fxmlTab: Tab          = _
  @FXML var documentationTab: Tab = _

  @FXML var sourceStackPane: StackPane = _
  @FXML var fxmlStackPane: StackPane   = _
  @FXML var documentationBox: HBox     = _

  val scalaCodeAreaWrapper: CodeAreaWrapper = CodeAreaWrapper(new CodeArea, CodeAreaWrapper.HighlightScala)
  val fxmlCodeAreaWrapper: CodeAreaWrapper  = CodeAreaWrapper(new CodeArea, CodeAreaWrapper.HighlightXML)

  var currentShowcaseItem: Option[ShowcaseItem] = None

  private var showcaseItems: List[ShowcaseItem] = _

  override def didGainVisibilityFirstTime(): Unit = {
    super.didGainVisibilityFirstTime()

    rootPane.getStylesheets.addAll(jfoenixCss, showcaseCss)

    fxmlStackPane.getStylesheets.add(xmlCss)
    fxmlStackPane.getChildren.add(fxmlCodeAreaWrapper.codeArea)
    sourceStackPane.getStylesheets.add(scalaCss)
    sourceStackPane.getChildren.add(scalaCodeAreaWrapper.codeArea)
    showcaseItemManager = ContentManager(showcasePane, this)

  }

  def updateShowcaseItems(items: List[ShowcaseItem]): Unit = {
    showcaseItems = items
    val rootTreeNode = new TreeItem[String]("Showcase Items")
    items.foreach { item =>
      val nameLeaf = new TreeItem[String](item.name)
      var found    = false
      rootTreeNode.getChildren.asScala.foreach { groupNode =>
        if (!found && groupNode.getValue.contentEquals(item.group)) {
          groupNode.getChildren.add(nameLeaf)
          found = true
        }
      }

      if (!found) {
        val groupNode = new TreeItem[String](item.group)
        groupNode.setExpanded(true)

        rootTreeNode.getChildren.add(groupNode)
        groupNode.getChildren.add(nameLeaf)
      }

    }
    selectionTreeview.setRoot(rootTreeNode)
    selectionTreeview.getSelectionModel.selectedItemProperty.addListener(new ChangeListener[TreeItem[String]] {
      override def changed(
          observableValue: ObservableValue[_ <: TreeItem[String]],
          oldValue: TreeItem[String],
          newValue: TreeItem[String]
      ): Unit = {
        val option = showcaseItems.find(item =>
          item.name == newValue.getValue &&
            item.group == newValue.parentProperty().get().getValue
        )
        option.foreach(item => changeShowcaseItem(item))
      }
    })

    rootTreeNode.setExpanded(true)

  }

  def updateShowcaseContent(controller: ViewController): Unit =
    showcaseItemManager.updatePaneContent(controller)

  def changeShowcaseItem(item: ShowcaseItem): Unit = {
    val start = System.currentTimeMillis()

    tabPane.getSelectionModel.select(showcaseTab)

    updateShowcaseContent(item.controller)
    showcaseTab.setText(item.name)
    val simpleName = item.controller.getClass.getSimpleName
    sourceTab.setText(simpleName)

    scalaCodeAreaWrapper.replaceText("not available")
    Try {
      val code = Source.fromURL(item.sourcePath).getLines.mkString("\n")
      scalaCodeAreaWrapper.replaceText(code)
    }
    fxmlCodeAreaWrapper.replaceText("not available")
    Try {
      val fxml = Source.fromURL(item.fxmlPath).getLines.mkString("\n")
      fxmlCodeAreaWrapper.replaceText(fxml)
    }

    Try {
      documentationBox.getChildren.clear()
      showcaseBottomBox.getChildren.clear()

      val docs          = Source.fromURL(item.documentationPath).getLines.mkString("\n")
      val documentation = "# %s - %s\n%s".format(item.group, item.name, docs)
      val markdown      = new MDFXNode(documentation)
      markdown.getStylesheets.add(markdownCss)
      markdown.setPadding(new Insets(4))
      documentationBox.getChildren.add(markdown)
      val shortDocs = {
        if (docs.indexOf("#") > 0 && !"WelcomeController".equals(simpleName)) {
          docs.substring(0, docs.indexOf("#"))
        }
        else {
          docs
        }
      }
      val markdownShort = new MDFXNode(shortDocs)
      markdownShort.getStylesheets.add(markdownCss)
      showcaseBottomBox.getChildren.add(markdownShort)
    }

    currentShowcaseItem = Some(item)

    val time = System.currentTimeMillis() - start
    statusLabel.setText("%s loaded in %s ms".format(simpleName, time))
  }
}
