package com.sfxcode.sapphire.core.demo.tutorial.controller

import javafx.collections.{FXCollections, ObservableList}
import javafx.fxml.FXML
import javafx.scene.chart.XYChart
class BarChartController extends AbstractViewController {

  @FXML
  var chart: javafx.scene.chart.BarChart[String, Number] = _

  override def didInitialize(): Unit = {
    super.didInitialize()
    chart.setData(createChartData())
  }

  private def createChartData(): ObservableList[javafx.scene.chart.XYChart.Series[String, Number]] = {
    var javaValue = 17.56
    var cValue = 17.06
    var cppValue = 8.25
    val answer = FXCollections.observableArrayList[javafx.scene.chart.XYChart.Series[String, Number]]()

    val java = new XYChart.Series[String, Number]()
    java.setName("Java")
    val c = new XYChart.Series[String, Number]()
    c.setName("C")
    val cpp = new XYChart.Series[String, Number]()
    cpp.setName("C++")

    for (i <- 2011 to 2016) {
      java.getData().add(new XYChart.Data[String, Number](i.toString, javaValue))
      javaValue += math.random - .5

      c.getData().add(new XYChart.Data[String, Number](i.toString, cValue))
      cValue += math.random - .5

      cpp.getData().add(new XYChart.Data[String, Number](i.toString, cppValue))
      cppValue += math.random - .5
    }
    answer.addAll(java, c, cpp)
    answer
  }

}
