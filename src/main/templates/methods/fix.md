# Guidelines for Mermaid Sequence Diagram Generation

* Produce documentation with Mermaid sequence diagrams for code functionality.
* Target audience: non-tech savvy. Ensure diagrams are easily understood and in plain English.
* Include relevant business rules/domain knowledge from code comments or logs in diagrams.
* Diagrams must clearly represent critical concepts. Use business domain-specific language when applicable.
* No method calls in descriptions so `Handler -> HRService : lookupEmployeeById()` not ok, but `Handler -> HRService : Lookup Employee By Id` is ok
* Participants should not be named Exception or Throwable and should not end in the word Exception
* Avoid participant aliases in diagrams. Use original class/object names from code.
* Exclude notes from diagrams. Encapsulate all necessary information within interaction sequence.
* Avoid 'activate'/'deactivate' commands in diagrams. Focus on participant interaction and action flow.
* Do not shorten/abbreviate names in diagrams. Use authentic names of classes/objects.
* Participants should be classes/objects with substantial actions/significant interactions.
* The start of the sequence is a client class if the method is public method
* The start of the sequence is the class of the method if it is a private method
* If sequences are long, then avoid data classes as participants: File, ChatRequest, EmbeddingRequest, AudioResponse, Article, Queue, String, StringNode, ObjectNode, etc.
* Do not use primitives as participants. Entities using/generating data are actual participants.
* Participants should include action-oriented classes like NewsService, ArticleSorter, etc.
* Replace Throwable/Exception with "Handled error", "Reported error".
* Do not use `System.out` as a participant
* Do not use primitives as a participant, e.g., do not use `byte[]`, `float`, `int`.
* Do not use basic data types as a participant, e.g., do not use `String`, `Integer`, `Float`.
* No angle brackets in participant: FAIL=`participant Optional<OfferEntity>`, PASS=`participant Optional~OfferEntity~`
* No angle brackets in message interaction: FAIL=`offerRepository-->>Optional<OfferEntity>: return Optional<OfferEntity>`, PASS=`offerRepository-->>Optional~OfferEntity~: might return an offer`
* No angle brackets in message interaction: FAIL=`offerRepository-->>Optional<OfferEntity>: return Optional<OfferEntity>`, PASS=`offerRepository-->>Optional~OfferEntity~: might return an offer`
* No dots in participant FAIL=`participant FacilityConfigProto.FacilityConfig`, PASS=`FacilityConfig`
* Always add a title.


# Fix Instructions
You just generated a broken mermaid diagram. The mermaid code and validation errors are included below.
Regenerate a mermaid sequence diagram based on the above guidelines titled {{TITLE}} using the Java code below by
following the instructions below:
1. `Participants`: Create a list of participants
   * Take out of that list any exceptions or throwables, and any primitives or basic data types like String.
   * Take out any `String` in participants or other basic types.
   * Ensure there are no angle brackets in the list of participants.
2. `Validation Fixes`: Go through each validation issue in `Validation JSON`, and plan and show how you are going to fix the problem this time.
4. `Participants After Fix`: Show the list of participants with any fixes applied from the `Fixes` section.
5. Now show the interaction between the participants using a markdown list, and explain each in plain English.
   * Ensure there are no angle brackets in the interactions. Replace <> with ~, i.e., List<Employee> to List~Employee~.
   * Ensure there are no method calls in the interaction description, just plain English.
   * Ensure that all `alt`/`else` have a corresponding `end`. 
   * Ensure that all `critical`/`option` have a corresponding `end`.
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
