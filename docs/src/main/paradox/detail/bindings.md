# KeyBindings

KeyBindings is a helper class for @ref:[FXBean Adapter](fxbean_adapter.md).
It provides Bindings from beans to ui representations.

It contains convenience functions for adding keys to @ref:[FXBean Adapter](fxbean_adapter.md) and for conversion.
## Code Sample

### FXML Snippet
```xml
<Label id="person"/>
<TextField id="name"/>
<TextField fx:id="age"/>

```
### Scala ViewController Snippet

@@snip [PersonController](../../../../../demos/tutorial/src/main/scala/com/sfxcode/sapphire/core/demo/tutorial/controller/PersonController.scala) { #bindingList }

## Create Bindings

Bindings are created by the value of the id or fx:id  attributes in the fxml file.

@@@ note

If node lookup from appication root node does not work, you can fix it by adding a parent Node to FXBeanAdapter.

@@snip [PersonController](../../../../../demos/tutorial/src/main/scala/com/sfxcode/sapphire/core/demo/tutorial/controller/PersonController.scala) { #adapter_create}

@@@



## Add Converter

@@snip [PersonController](../../../../../demos/tutorial/src/main/scala/com/sfxcode/sapphire/core/demo/tutorial/controller/PersonController.scala) { #addConverter }
