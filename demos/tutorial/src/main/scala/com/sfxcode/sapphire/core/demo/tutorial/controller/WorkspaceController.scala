package com.sfxcode.sapphire.core.demo.tutorial.controller

import javafx.fxml.FXML

class WorkspaceController extends AbstractViewController {

  @FXML
  var chart: javafx.scene.chart.PieChart = _

  //  val pieChartDataBuffer = ObservableBuffer(
  //    PieChart.Data("Grapefruit", 20),
  //    PieChart.Data("Oranges", 30),
  //    PieChart.Data("Plums", 10),
  //    PieChart.Data("Apples", 40))
  //
  //  override def didInitialize(): Unit = {
  //    super.didInitialize()
  //    chart.setData(pieChartDataBuffer)
  //  }
}
