package com.sfxcode.sapphire.core.demo.issues.model

import com.sfxcode.sapphire.core.value.FXBean
import scalafx.collections.ObservableBuffer
import scalafx.collections.ObservableMap._
import java.util.concurrent.atomic.AtomicInteger
import com.sfxcode.sapphire.core.value.FXBeanCollections._
import com.sfxcode.sapphire.core.Includes._

case class Issue(id: String, projectName: String, var status: String, var synopsis: String, var description: String)


object IssueDataBase {

  val issueCounter = new AtomicInteger(0)
  val projectsMap = observableBufferMap[String, Issue]
  List("Project1", "Project2", "Project3", "Project4").foreach(projectsMap.put(_, observableBuffer[Issue]))

  val projectNames = ObservableBuffer[String]()
  projectNames.++=:(projectsMap.keys)
  projectsMap.onChange((_, change) => {
    change match {
      case Add(key, added) => projectNames.+=(key)
      case Remove(key, removed) => projectNames.-=(key)
      case _ =>
    }
  })

  val issuesMap = observableMap[String, Issue]
  issuesMap.onChange((_, change) => {
    change match {
      case Add(key, added) => projectsMap(added.bean.projectName).+=(added)
      case Remove(key, removed) => projectsMap(removed.bean.projectName).-=(removed)
      case _ =>
    }
  })

  def createIssue(projectName: String, synopsis: String = "", description: String = ""): FXBean[Issue] = {
    assert(projectNames.contains(projectName))
    val issue = Issue("TT-%s".format(issueCounter.incrementAndGet()), projectName, "NEW", synopsis, description)
    assert(!issuesMap.containsKey(issue.id))
    assert(!projectsMap(projectName).contains(issue.id))
    val bean: FXBean[Issue] = issue
    issuesMap.put(issue.id, bean)
    bean
  }

  def updateIssue(issueId: String) {
     // not needed => update always
  }

  def deleteIssue(issueId: String) {
    assert(issuesMap.containsKey(issueId))
    issuesMap.-=(issueId)
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
