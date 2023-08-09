----
# FIX RAW RESPONSE 2
# Participants

1. JavaItem
2. List~String~
3. ArrayList~String~

# Validation Fixes
1. Rename `List~String~` to `ListString`
2. Rename `ArrayList~String~` to `ArrayListString`

# Interaction After Fix
```mermaid
---
title: Interaction After Fix
---

sequenceDiagram
    participant JavaItem
    participant ListString
    participant ArrayListString

    JavaItem->>ListString: store importBody
    JavaItem->>ListString: store body
    JavaItem->>ListString: store javadoc
    JavaItem->>ListString: store name
    JavaItem->>ListString: store simpleName
    JavaItem->>ListString: store definition
    JavaItem->>ListString: store parent
    JavaItem->>ListString: store type
    JavaItem->>ArrayListString: add "Name"
    JavaItem->>ArrayListString: add "Type"
    JavaItem->>ArrayListString: add "FullName"
    JavaItem->>ArrayListString: add "Definition"
    JavaItem->>ArrayListString: add "JavaDoc"
    JavaItem->>ArrayListString: add "Parent"
    JavaItem->>ArrayListString: add "Imports"
    JavaItem->>ArrayListString: add "Body"
    JavaItem->>String: format "Name"
    JavaItem->>String: format "Type"
    JavaItem->>String: format "FullName"
    JavaItem->>String: format "Definition"
    JavaItem->>String: format "JavaDoc"
    JavaItem->>String: format "Parent"
    JavaItem->>String: format "Imports"
    JavaItem->>String: format "Body"
```

# Final Participants

1. JavaItem
2. ListString
3. ArrayListString

# Plain English Title

"JavaItem Headers"

# Mermaid Sequence Diagram

```mermaid
---
title: JavaItem Headers
---

sequenceDiagram
    participant JavaItem
    participant ListString
    participant ArrayListString

    JavaItem->>ListString: store importBody
    JavaItem->>ListString: store body
    JavaItem->>ListString: store javadoc
    JavaItem->>ListString: store name
    JavaItem->>ListString: store simpleName
    JavaItem->>ListString: store definition
    JavaItem->>ListString: store parent
    JavaItem->>ListString: store type
    JavaItem->>ArrayListString: add "Name"
    JavaItem->>ArrayListString: add "Type"
    JavaItem->>ArrayListString: add "FullName"
    JavaItem->>ArrayListString: add "Definition"
    JavaItem->>ArrayListString: add "JavaDoc"
    JavaItem->>ArrayListString: add "Parent"
    JavaItem->>ArrayListString: add "Imports"
    JavaItem->>ArrayListString: add "Body"
    JavaItem->>String: format "Name"
    JavaItem->>String: format "Type"
    JavaItem->>String: format "FullName"
    JavaItem->>String: format "Definition"
    JavaItem->>String: format "JavaDoc"
    JavaItem->>String: format "Parent"
    JavaItem->>String: format "Imports"
    JavaItem->>String: format "Body"

```