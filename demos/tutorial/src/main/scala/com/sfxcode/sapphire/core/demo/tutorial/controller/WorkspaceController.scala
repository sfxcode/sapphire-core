package com.sfxcode.sapphire.core.demo.tutorial.controller

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.chart.PieChart

class WorkspaceController extends AbstractViewController {

  val pieChartDataBuffer = FXCollections.observableArrayList(new PieChart.Data("Grapefruit", 20),
                                                             new PieChart.Data("Oranges", 30),
                                                             new PieChart.Data("Plums", 10),
                                                             new PieChart.Data("Apples", 40))
  @FXML
  var chart: javafx.scene.chart.PieChart = _

  override def didInitialize(): Unit = {
    super.didInitialize()
    chart.setData(pieChartDataBuffer)
  }
}
