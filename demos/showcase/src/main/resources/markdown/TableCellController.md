FXTableCellFactory usage in Tables created from FXML.


## Code Example

#### simpleClassName is used for TableCell instance creation, here: CheckBoxTableCell (default: TextFieldTableCell)

#### alignment is used for TableCell position

#### converter can be used for existing converter name registered by ConverterProvider

```
 <TableColumn  text="Checkbox Active">
    <cellValueFactory>
        <FXTableValueFactory property="isActive" />
    </cellValueFactory>
    <cellFactory>
        <FXTableCellFactory simpleClassName="CheckBoxTableCell" alignment="right"/>
    </cellFactory>
</TableColumn>
```
