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
* Avoid System.out or primitives byte[], float, int as participants.
* No angle brackets in participant: FAIL=`participant Optional<OfferEntity>`, PASS=`participant Optional~OfferEntity~`
* No angle brackets in message interaction: FAIL=`offerRepository-->>Optional<OfferEntity>: return Optional<OfferEntity>`, PASS=`offerRepository-->>Optional~OfferEntity~: might return an offer`
* No angle brackets in message interaction: FAIL=`offerRepository-->>Optional<OfferEntity>: return Optional<OfferEntity>`, PASS=`offerRepository-->>Optional~OfferEntity~: might return an offer`
* No dots in participant FAIL=`participant FacilityConfigProto.FacilityConfig`, PASS=`FacilityConfig`
* Always add a title


# Fix Instructions
You just generated a broken mermaid diagram. The mermaid code and validation errors are included below.
Regenerate a mermaid sequence diagram based on the above guidelines titled {{TITLE}} using the Java code below by
following the instructions below:
1. Create a list of participants
2. Take out of that list any exceptions or throwables, and any primitives.
3. Ensure there are no angle brackets in the list of participants.
4. Show the list of participants.
5. Now show the interaction between the participants using a markdown list, and explain each in plain English.
6. Ensure there are no angle brackets in the interactions. Replace <> with ~, i.e., List<Employee> to List~Employee~.
7. Ensure there are no method calls in the interaction description, just plain English.
8. Create a plain English title based on  {{TITLE}}, "This method does "
9. Finally, create the mermaid code for the sequence diagram and make sure it uses the plain English title.

# Broken Mermaid
```mermaid

{{MERMAID}}

```

# Java method 

```java

{{JAVA_METHOD}}

```


# Instruction
Regenerate a mermaid sequence diagram based on the above guidelines and the validation results titled {{TITLE}}


# Validation JSON

```javascript 

{{JSON}}

```
