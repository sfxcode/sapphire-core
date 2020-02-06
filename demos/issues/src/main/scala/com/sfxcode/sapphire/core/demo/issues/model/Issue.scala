package com.sfxcode.sapphire.core.demo.issues.model

import java.util.concurrent.atomic.AtomicInteger

import com.sfxcode.sapphire.core.CollectionExtensions._
import com.sfxcode.sapphire.core.value.FXBeanCollections._
import com.sfxcode.sapphire.core.value.{BeanConversions, FXBean}
import javafx.collections.{FXCollections, ObservableList, ObservableMap}

case class Issue(id: String, projectName: String, var status: String, var synopsis: String, var description: String)

object IssueDataBase extends BeanConversions {

  val issueCounter = new AtomicInteger(0)
  val projectsMap: ObservableMap[String, ObservableList[FXBean[Issue]]] =
    FXCollections.observableHashMap[String, ObservableList[FXBean[Issue]]]
  List("Project1", "Project2", "Project3", "Project4").foreach(projectsMap.put(_, observableList[Issue]))

  val projectNames: ObservableList[String] = FXCollections.observableArrayList[String]
  projectNames.addAll(projectsMap.keySet())

  projectsMap.addMapChangeListener { (state, key, _, _) =>
    if (ChangeState.ADD.equals(state)) {
      projectNames.add(key)
    }
    else if (ChangeState.REMOVE.equals(state)) {
      projectNames.remove(key)
    }
  }

  val issuesMap: ObservableMap[String, FXBean[Issue]] = observableMap[String, Issue]
  issuesMap.addMapChangeListener { (state, _, newValue, oldValue) =>
    if (ChangeState.ADD.equals(state)) {
      projectsMap.get(newValue.projectName).add(newValue)
    }
    else if (ChangeState.REMOVE.equals(state)) {
      projectsMap.get(oldValue.projectName).remove(oldValue)
    }
  }

  def createIssue(projectName: String, synopsis: String = "", description: String = ""): FXBean[Issue] = {
    assert(projectNames.contains(projectName))
    val issue = Issue("TT-%s".format(issueCounter.incrementAndGet()), projectName, "NEW", synopsis, description)
    assert(!issuesMap.containsKey(issue.id))
    assert(!projectsMap.get(projectName).contains(issue.id))
    val bean: FXBean[Issue] = issue
    issuesMap.put(issue.id, bean)
    bean
  }

  def updateIssue(issueId: String) {
    // not needed => update always
  }

  def deleteIssue(issueId: String) {
    if (issuesMap.containsKey(issueId)) {
      issuesMap.remove(issueId)
    }
  }

  createIssue(
    "Project1",
    "We rode in sorrow, with strong hounds three",
    "From \"The Wanderings Of Oisin\".\nW. B. Yeats."
  )
  createIssue("Project2", "Bran, Sgeolan, and Lomair", "From \"The Wanderings Of Oisin\".\nW. B. Yeats.")
  createIssue("Project3", "On a morning misty and mild and fair", "From \"The Wanderings Of Oisin\".\nW. B. Yeats.")
  createIssue(
    "Project4",
    "The mist-drops hung on the fragrant trees",
    "From \"The Wanderings Of Oisin\".\nW. B. Yeats."
  )
  createIssue("Project3", "And in the blossoms hung the bees", "From \"The Wanderings Of Oisin\".\nW. B. Yeats.")
  createIssue("Project2", "We rode in sadness above Lough Lean", "From \"The Wanderings Of Oisin\".\nW. B. Yeats.")
  createIssue("Project1", "For our best were dead on Gavra's green", "From \"The Wanderings Of Oisin\".\nW. B. Yeats.")
  createIssue("Project4", "The Wanderings of Oisin", "William Butler Yeats.")

}
