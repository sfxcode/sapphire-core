FXValueFactory usage in Tables created from FXML.

Shows simple Bindings and Expression Bindings.


## Code Example

#### Value is resoved by property value, here: age

```
<TableColumn prefWidth="50.0" text="Age">
    <cellValueFactory>
        <FXValueFactory property="age"/>
    </cellValueFactory>
</TableColumn>
```

## Format Example

#### Format is resoved by format value, here: 0000


```
<TableColumn prefWidth="50.0" text="Age">
    <cellValueFactory>
        <FXValueFactory property="age" format="0000"/>
    </cellValueFactory>
</TableColumn>
```

## Expression Example

####  Note: Normally Expressions in sapphire-core have the following syntax:

```
!{_self.name()}
```

#### Because of existing usage in FXML of the $ Symbol ! is used (it is changed internal later by $)
```
<TableColumn prefWidth="250.0" text="Description">
    <cellValueFactory>
        <FXValueFactory property="Name: !{_self.name()} Age: !{_self.age()} (!{_self.id()}) "/>
    </cellValueFactory>
</TableColumn>
```