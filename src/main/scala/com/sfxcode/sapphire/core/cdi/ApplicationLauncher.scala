package com.sfxcode.sapphire.core.cdi

import com.sfxcode.sapphire.core.cdi.annotation.FXStage
import javafx.stage.Stage
import javax.annotation.PostConstruct
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.event.Event
import javax.enterprise.util.AnnotationLiteral
import javax.inject.Inject
import org.apache.deltaspike.core.api.projectstage.ProjectStage

@ApplicationScoped
class ApplicationLauncher {

  @Inject
  var projectStage: ProjectStage = _

  @Inject
  var events: Event[Any] = _

  @PostConstruct
  def start() {}

  def launch(primaryStage: Stage, literal: AnnotationLiteral[_]) {
    val stageLiteral = new AnnotationLiteral[FXStage] {}
    events.select(classOf[Stage], stageLiteral, literal).fire(primaryStage)
  }
}
