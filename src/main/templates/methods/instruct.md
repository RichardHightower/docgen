
# Instructions
Generate a mermaid sequence diagram based on the above guidelines using the Java code below by 
following the instructions below:
1. Create a list of participants
   * Take out of that list any participants that are exceptions or throwables, and any primitives.
   * Take out of that list any participants that are basic data types like `String`.
   * Ensure there are no angle brackets in the list of participants `<` `>` can be changed to `~`.
2. `Participants`: Show the list of participants.
3. `Initial Interactions` : List all interactions between the participants in markdown and explain in plain English
4. `Clean Interactions`: Now clean up the `Initial Interactions` between the participants 
5. `Final Participants`: Show the list of participants from `Participants` with participants removed if participant is not used in `Clean Interaction`.
   * Add sub-bullet for each participant with a sub list of the interaction it is mentioned in from `Clean Interaction`.
   * If there are no interactions in the sub-list than it should not be on this list so add an extra sub-bullet with the text `REMOVE`.
6. `Plain English Title`: Create a plain English title based on  "{{TITLE}}".
   * No method calls so nothing like `toString()`
   * No parenthesis: NO '(' NO ')'
7. Finally, create the mermaid code for the sequence diagram

# Java method 

```java

{{JAVA_METHOD}}

```

# Participants
(format markdown)

# Initial Interactions
(format markdown)

# Clean Interactions
(format markdown)

# Final Participants
(format markdown)

# Plain English Title
(format markdown)

# Mermaid Sequence Diagram
(format mermaid markup below)

```mermaid
---
title: {{Plain English Title}}
---

sequenceDiagram
    {{Final Participants}}
    {{Clean Interactions}}
```

End of instructions.
