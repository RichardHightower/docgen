----
# ORIGINAL RESPONSE 
# Participants

* Builder
* Parent

# Initial Interactions

* Builder creates an instance of JavaItem
* JavaItem class has importBody, body, javadoc, name, simpleName, definition, parent, and type fields

# Clean Interactions

* Builder creates an instance of JavaItem
* JavaItem class has importBody, body, javadoc, name, simpleName, definition, parent, and type fields

# Final Participants

* Builder
  * Instance of JavaItem
* JavaItem
  * importBody
  * body
  * javadoc
  * name
  * simpleName
  * definition
  * parent
  * type

# Plain English Title

Builder creates an instance of JavaItem and sets its fields

# Mermaid Sequence Diagram

```mermaid
---
title: Builder creates an instance of JavaItem
---

sequenceDiagram
    participant Builder
    participant JavaItem

    Builder->>JavaItem: Create an instance of JavaItem
    Builder->>JavaItem: Set importBody field
    Builder->>JavaItem: Set body field
    Builder->>JavaItem: Set javadoc field
    Builder->>JavaItem: Set name field
    Builder->>JavaItem: Set simpleName field
    Builder->>JavaItem: Set definition field
    Builder->>JavaItem: Set parent field
    Builder->>JavaItem: Set type field
```

