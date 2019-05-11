package com.sfxcode.sapphire.core.demo.tutorial.controller

import javafx.fxml.FXML
import scalafx.Includes._
import scalafx.collections.ObservableBuffer
import scalafx.scene.chart.XYChart

class BarChartController extends AbstractViewController {

  @FXML
  var chart: javafx.scene.chart.BarChart[String, Number] = _

  override def didInitialize(): Unit = {
    super.didInitialize()
    chart.setData(createChartData())
  }

  private def createChartData(): ObservableBuffer[javafx.scene.chart.XYChart.Series[String, Number]] = {
    var javaValue = 17.56
    var cValue = 17.06
    var cppValue = 8.25
    val answer = new ObservableBuffer[javafx.scene.chart.XYChart.Series[String, Number]]()
    val java = new XYChart.Series[String, Number] {
      name = "Java"
    }
    val c = new XYChart.Series[String, Number] {
      name = "C"
    }
    val cpp = new XYChart.Series[String, Number] {
      name = "C++"
    }
    for (i <- 2011 to 2016) {
      java.data() += XYChart.Data[String, Number](i.toString, javaValue)
      javaValue += math.random - .5

      c.data() += XYChart.Data[String, Number](i.toString, cValue)
      cValue += math.random - .5

      cpp.data() += XYChart.Data[String, Number](i.toString, cppValue)
      cppValue += math.random - .5
    }
    answer.addAll(java, c, cpp)
    answer
  }

}
