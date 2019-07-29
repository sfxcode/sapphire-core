package com.sfxcode.sapphire.core.demo.issues.model

import java.util.concurrent.atomic.AtomicInteger

import com.sfxcode.sapphire.core.Includes._
import com.sfxcode.sapphire.core.value.FXBean
import com.sfxcode.sapphire.core.value.FXBeanCollections._
import javafx.collections.{FXCollections, MapChangeListener, ObservableList}

case class Issue(id: String, projectName: String, var status: String, var synopsis: String, var description: String)

object IssueDataBase {

  val issueCounter = new AtomicInteger(0)
  val projectsMap = FXCollections.observableHashMap[String, ObservableList[FXBean[Issue]]]
  List("Project1", "Project2", "Project3", "Project4").foreach(projectsMap.put(_, observableList[Issue]))

  val projectNames = FXCollections.observableArrayList[String]
  projectNames.addAll(projectsMap.keySet())

  projectsMap.addListener(new MapChangeListener[String, ObservableList[FXBean[Issue]]] {
    def onChanged(change: MapChangeListener.Change[_ <: String, _ <: ObservableList[FXBean[Issue]]]): Unit = {
      if (change.wasAdded()) {
        projectNames.add(change.getKey)
      }
      else if (change.wasRemoved()) {
        projectNames.remove(change.getKey)
      }
    }
  })


  val issuesMap = observableMap[String, Issue]
  issuesMap.addListener(new MapChangeListener[String, FXBean[Issue]] {
    def onChanged(change: MapChangeListener.Change[_ <: String, _ <: FXBean[Issue]]): Unit = {
      val key = change.getKey
      if (change.wasAdded()) {
        val added = change.getValueAdded
        projectsMap.get(added.projectName).add(added)
      }
      else if (change.wasRemoved()) {
        val removed = change.getValueRemoved
        projectsMap.get(removed.projectName).remove(removed)
      }
    }
  })


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
    if (issuesMap.containsKey(issueId))
      issuesMap.remove(issueId)
  }

  createIssue("Project1", "We rode in sorrow, with strong hounds three",
    "From \"The Wanderings Of Oisin\".\nW. B. Yeats.")
  createIssue("Project2", "Bran, Sgeolan, and Lomair",
    "From \"The Wanderings Of Oisin\".\nW. B. Yeats.")
  createIssue("Project3", "On a morning misty and mild and fair",
    "From \"The Wanderings Of Oisin\".\nW. B. Yeats.")
  createIssue("Project4", "The mist-drops hung on the fragrant trees",
    "From \"The Wanderings Of Oisin\".\nW. B. Yeats.")
  createIssue("Project3", "And in the blossoms hung the bees",
    "From \"The Wanderings Of Oisin\".\nW. B. Yeats.")
  createIssue("Project2", "We rode in sadness above Lough Lean",
    "From \"The Wanderings Of Oisin\".\nW. B. Yeats.")
  createIssue("Project1", "For our best were dead on Gavra's green",
    "From \"The Wanderings Of Oisin\".\nW. B. Yeats.")
  createIssue("Project4", "The Wanderings of Oisin",
    "William Butler Yeats.")


}
