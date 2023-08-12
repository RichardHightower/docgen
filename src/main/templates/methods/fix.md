

# Instructions To Fix Mermaid Sequence Diagram
You just generated a broken mermaid diagram. The mermaid code and validation errors are included below.
Regenerate a mermaid sequence diagram based on the above guidelines titled {{TITLE}} using the Java code below by
following the instructions below:
1. `Participants`: Create a list of participants
2. `Validation Fixes`: Go through each validation issue in `Validation JSON`, and plan and show how you are going to fix the problem this time.
4. `Participants After Fix`: Show the list of participants with any fixes applied from the `Fixes` section.
5. Now show the interaction between the participants using a markdown list, and explain each in plain English.
6. `Interaction After Fix`: Show the list of interaction with any fixes applied from the `Validation Fixes` section.
7. `Final Participants`: Show the list of participants from `Participants After Fix` with participants removed if participant is not used in `Interaction After Fix` .
8. `Plain English Title`: Create a plain English title based on  "{{TITLE}}".
   * No method calls so nothing like `toString()`
   * No parenthesis: NO '(' NO ')'. 
9. Finally, create the mermaid code for the sequence diagram
   * make sure it uses the `Plain English Title` for the title. 
   * make sure it uses participants from the plain `Final Participants`.
   * Do not add `Notes`.
   * Make sure it uses interactions from `Interaction After Fix`.
      * Ensure that all `alt`/`else` have a corresponding `end`. 
      * Ensure that all `critical`/`option` have a corresponding `end`.


# Broken Mermaid
```mermaid

{{MERMAID}}

```

# Java method 

```java

{{JAVA_METHOD}}

```



# Validation JSON

```javascript 

{{JSON}}

```




# Participants
(format markdown)

# Validation Fixes
(format markdown)

# Interaction After Fix
(format markdown)

# Final Participants
(format markdown)

# Plain English Title
(format markdown)

# Mermaid Sequence Diagram

```mermaid
---
title: {{Plain English Title}}
---

sequenceDiagram
    {{Final Participants}}
    {{Clean Interactions}}
```
End of instructions.
