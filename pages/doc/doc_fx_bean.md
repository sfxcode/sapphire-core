---
title: FXBean
tags: [info, tutorial]
keywords: JUEL, PersonControllerController, FXBean
last_updated: October 7, 2015
summary: "Step by step tutorial - FXBean"

---
## FXBean

The Blueprint Application demonstrates the use of FXBean, which is used as wrapper around Scala/Java classes to generate automatically JavaFX Properties for binding purposes.

## Use of FXBean in TableViews

The PersonController creates a TableView instance by fxml loading with items of type FXBean[Person].

When the controller is visible the first time, the items (Scala List of Persons) are set. Implicit Conversion to ObservableBuffer[FXBean[Person]] is done by importing sapphire core Includes.

```scala

import com.sfxcode.sapphire.core.Includes._

class PersonController extends AbstractViewController {

  @FXML
  var tableView: TableView[FXBean[Person]] = _

  def items: ObservableBuffer[FXBean[Person]] = PersonFactory.personList

  override def didGainVisibilityFirstTime(): Unit = {
    super.didGainVisibilityFirstTime()
    ...
    tableView.setItems(items)
  }

  ...
}

```

The representing FXML shows the usage of FXValueFactory.

```xml
            <TableView fx:id="tableView" prefHeight="200.0" prefWidth="200.0">
              <columns>
                <TableColumn prefWidth="150.0" text="ID">
                    <cellValueFactory>
                        <FXValueFactory property="id" />
                    </cellValueFactory>
                        </TableColumn>
                  <TableColumn prefWidth="150.0" text="Name">
                      <cellValueFactory>
                          <FXValueFactory property="name" />
                      </cellValueFactory>
                  </TableColumn>
                  <TableColumn prefWidth="50.0" text="Age">
                      <cellValueFactory>
                          <FXValueFactory property="age" />
                      </cellValueFactory>
                  </TableColumn>
                  <TableColumn prefWidth="200.0" text="Description">
                      <cellValueFactory>
                          <FXValueFactory property="Name: !{_self.name()} Age: !{_self.age()}"/>
                      </cellValueFactory>
                  </TableColumn>
              </columns>
            </TableView>


```


... to be continued !!!

 


